package serverNonBlocking.transport.selector;

import serverNonBlocking.transport.TransportException;
import serverNonBlocking.transport.WatchdogTimer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.function.Consumer;

public class BytesInboundHandler implements InboundHandler {
    public static final int BUFFER_SIZE = 8192;

    private final long operId;
    private final SelectionKey key;
    private long bytesToReceive;
    private final Consumer<ByteBuffer> continueCallback;
    private final Runnable finishedCallback;
    private final ByteBuffer buffer;

    public BytesInboundHandler(long operId,
                               SelectionKey key,
                               long bytesToReceive,
                               Consumer<ByteBuffer> continueCallback,
                               Runnable finishedCallback) {
        this.operId = operId;
        this.key = key;
        this.bytesToReceive = bytesToReceive;
        this.continueCallback = continueCallback;
        this.finishedCallback = finishedCallback;
        buffer = ByteBuffer.wrap(new byte[BUFFER_SIZE]);
    }

    @Override
    public void inbound() {
        WatchdogTimer.instance().resetOperationCountdown(operId);
        try {
            var channel = (SocketChannel) key.channel();

            long bytesReadCount;
            buffer.clear();
            if (bytesToReceive < BUFFER_SIZE) {
                buffer.limit((int) bytesToReceive);
            }
            while (bytesToReceive > 0 && (bytesReadCount = channel.read(buffer)) > 0) {
                buffer.flip();
                continueCallback.accept(buffer);
                bytesToReceive -= bytesReadCount;
                buffer.clear();
                if (bytesToReceive < BUFFER_SIZE) {
                    buffer.limit((int) bytesToReceive);
                }
            }

            if (bytesToReceive == 0) {
                key.interestOpsAnd(~SelectionKey.OP_READ);
                WatchdogTimer.instance().cancel(operId);
                finishedCallback.run();
            }
        } catch (IOException e) {
            throw new TransportException(e.getMessage(), e);
        }
    }
}
