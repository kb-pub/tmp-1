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

class MessageOutboundHandler implements OutboundHandler {
    private final long operId;
    private final SelectionKey key;
    private final Message sendMessage;
    private final MessageSerializer messageSerializer;
    private final Runnable sendCallback;
    private ByteBuffer sendBuffer;

    public MessageOutboundHandler(long operId,
                                  SelectionKey key,
                                  Message sendMessage,
                                  MessageSerializer messageSerializer,
                                  Runnable sendCallback) {
        this.operId = operId;
        this.key = key;
        this.sendMessage = sendMessage;
        this.messageSerializer = messageSerializer;
        this.sendCallback = sendCallback;
    }

    @Override
    public void outbound() throws SerializationException {
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
                throw new TransportException("unexpected end of channel while sending a message");
            }

            if (!sendBuffer.hasRemaining()) {
                key.interestOpsAnd(~SelectionKey.OP_WRITE);
                WatchdogTimer.instance().cancel(operId);
                sendCallback.run();
            }
        } catch (SerializationException e) {
            throw e;
        } catch (IOException e) {
            throw new TransportException(e.getMessage(), e);
        }
    }
}
