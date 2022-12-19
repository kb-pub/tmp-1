package serverNonBlocking.transport.selector;

import message.Message;
import message.serialization.MessageSerializer;
import message.serialization.SerializationException;
import serverNonBlocking.transport.TransportException;
import serverNonBlocking.transport.WatchdogTimer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.function.Consumer;

public class MessageInboundHandler implements InboundHandler {
    private final long operationId;
    private final SelectionKey key;
    private final MessageSerializer messageSerializer;
    private final Consumer<Message> callback;
    private ByteBuffer buffer = null;

    public MessageInboundHandler(long operationId,
                                 SelectionKey key,
                                 MessageSerializer messageSerializer,
                                 Consumer<Message> callback) {
        this.operationId = operationId;
        this.key = key;
        this.messageSerializer = messageSerializer;
        this.callback = callback;
    }

    @Override
    public void inbound() throws SerializationException {
        try {
            var channel = (SocketChannel) key.channel();

            if (buffer == null) { // no message receiving right now
                buffer = ByteBuffer.allocate(4);
                if (channel.read(buffer) < 4) {
                    throw new TransportException("too few bytes to inbound message length");
                }
                buffer.rewind();
                var messageLength = buffer.getInt();
                if (messageLength <= 0) {
                    throw new TransportException("too few bytes for inbound message");
                }
                buffer = ByteBuffer.wrap(new byte[messageLength]);
            }

            // continue receiving message
            int receivedBytesCount = channel.read(buffer);
            if (receivedBytesCount < 0) {
                throw new TransportException("unexpected inbound eof");
            }

            if (!buffer.hasRemaining()) { // full message received
                var bytes = buffer.array();
                buffer = null;
                key.interestOpsAnd(~SelectionKey.OP_READ);
                WatchdogTimer.instance().cancel(operationId);
                var message = messageSerializer.deserialize(bytes);
                callback.accept(message);
            }
        } catch (SerializationException e) {
            throw e;
        } catch (IOException e) {
            throw new TransportException(e.getMessage(), e);
        }
    }
}
