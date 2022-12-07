package serverSelector.transport;

import exception.AppException;
import message.Message;
import message.serialization.MessageSerializer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.function.Consumer;

public class SelectorTransport implements Transport {
    private final SelectionKey key;
    private final MessageSerializer messageSerializer;

    private Message sendMessage;
    private Runnable sendCallback;
    private ByteBuffer sendBuffer = null;

    private Consumer<Message> receiveCallback;
    private final ByteBuffer messageLengthBuffer = ByteBuffer.allocate(4);
    private ByteBuffer receiveBuffer = null;

    public SelectorTransport(SelectionKey key, MessageSerializer messageSerializer) {
        this.key = key;
        this.messageSerializer = messageSerializer;
    }

    @Override
    public void send(Message message, Runnable callback) {
        sendMessage = message;
        sendCallback = callback;
        key.interestOpsOr(SelectionKey.OP_WRITE);
    }

    @Override
    public void receive(Consumer<Message> callback) {
        receiveCallback = callback;
        key.interestOpsOr(SelectionKey.OP_READ);
    }

    @Override
    public void receiveBytes(long count, Consumer<ByteBuffer> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void inbound() {
        try {
            var channel = (SocketChannel) key.channel();

            if (receiveBuffer == null) { // no message receiving right now
                messageLengthBuffer.clear();
                if (channel.read(messageLengthBuffer) < 4) {
                    throw new AppException("too few bytes to inbound message length");
                }
                messageLengthBuffer.rewind();
                var messageLength = messageLengthBuffer.getInt();
                if (messageLength <= 0) {
                    throw new AppException("too few bytes for inbound message");
                }
                receiveBuffer = ByteBuffer.wrap(new byte[messageLength]);
            }

            // continue receiving message
            int receivedBytesCount = channel.read(receiveBuffer);
            if (receivedBytesCount < 0) {
                throw new AppException("unexpected inbound eof");
            }

            if (!receiveBuffer.hasRemaining()) { // full message received
                var bytes = receiveBuffer.array();
                receiveBuffer = null;
                key.interestOpsAnd(~SelectionKey.OP_READ);
                var message = messageSerializer.deserialize(bytes);
                receiveCallback.accept(message);
            }
        } catch (IOException e) {
            throw new AppException(e.getMessage(), e);
        }
    }

    @Override
    public void outbound() {
        if (sendMessage == null) {
            throw new AppException("no message to send");
        }

        try {
            var channel = (SocketChannel) key.channel();

            if (sendBuffer == null) { // new message to send
                var messageBodyBytes = messageSerializer.serialize(sendMessage);
                var messageLength = messageBodyBytes.length;
                var bytes = new byte[messageLength + 4];
                System.arraycopy(messageBodyBytes, 0, bytes, 4, messageLength);
                sendBuffer = ByteBuffer.wrap(bytes);
                sendBuffer.putInt(messageLength);
                sendBuffer.clear();
            }

            // continue sending
            var sendingByteCount = channel.write(sendBuffer);
            if (sendingByteCount < 0) {
                throw new AppException("unexpected end of channel while sending a message");
            }

            if (!sendBuffer.hasRemaining()) {
                key.interestOpsAnd(~SelectionKey.OP_WRITE);
                sendMessage = null;
                sendBuffer = null;
                sendCallback.run();
            }
        }
        catch (IOException e) {
            throw new AppException(e.getMessage(), e);
        }
    }

    @Override
    public void close() {
        key.cancel();
    }
}
