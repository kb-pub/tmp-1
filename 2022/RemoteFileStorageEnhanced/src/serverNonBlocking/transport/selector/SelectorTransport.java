package serverNonBlocking.transport.selector;

import message.ErrorMessage;
import message.Message;
import message.serialization.MessageSerializer;
import message.serialization.SerializationException;
import serverNonBlocking.transport.Transport;
import serverNonBlocking.transport.TransportException;
import serverNonBlocking.transport.WatchdogTimer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.util.function.Consumer;
import java.util.function.Function;

public class SelectorTransport implements Transport {
    private final SelectionKey key;
    private final MessageSerializer messageSerializer;

    private OutboundHandler outboundHandler;
    private InboundHandler inboundHandler;

    public SelectorTransport(SelectionKey key, MessageSerializer messageSerializer) {
        this.key = key;
        this.messageSerializer = messageSerializer;
    }

    @Override
    public void sendMessage(Message message, Runnable finishedCallback) {
        var operId = WatchdogTimer.instance().newOperationCountdown(this);
        outboundHandler = new MessageOutboundHandler(operId, key, message, messageSerializer, finishedCallback);
        key.interestOpsOr(SelectionKey.OP_WRITE);
    }

    @Override
    public void sendBytes(Function<ByteBuffer, Boolean> continueCallback, Runnable finishedCallback) {
        var operId = WatchdogTimer.instance().newOperationCountdown(this);
        outboundHandler = new BytesOutboundHandler(operId, key, continueCallback, finishedCallback);
        key.interestOpsOr(SelectionKey.OP_WRITE);
    }

    @Override
    public void receiveMessage(Consumer<Message> finishedCallback) {
        var operId = WatchdogTimer.instance().newOperationCountdown(this);
        inboundHandler = new MessageInboundHandler(operId, key, messageSerializer, finishedCallback);
        key.interestOpsOr(SelectionKey.OP_READ);
    }

    @Override
    public void receiveBytes(long count,
                             Consumer<ByteBuffer> continueCallback,
                             Runnable finishedCallback) {
        var operId = WatchdogTimer.instance().newOperationCountdown(this);
        inboundHandler = new BytesInboundHandler(operId, key, count, continueCallback, finishedCallback);
        key.interestOpsOr(SelectionKey.OP_READ);
    }

    @Override
    public void inbound() {
        try {
            inboundHandler.inbound();
        } catch (SerializationException e) {
            sendMessage(new ErrorMessage("message serialization error"), this::close);
        } catch (TransportException e) {
            close();
        }
    }

    @Override
    public void outbound() {
        try {
            outboundHandler.outbound();
        } catch (SerializationException e) {
            sendMessage(new ErrorMessage("message serialization error"), this::close);
        } catch (TransportException e) {
            close();
        }
    }

    @Override
    public void close() {
        try {
            key.cancel();
            key.channel().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
