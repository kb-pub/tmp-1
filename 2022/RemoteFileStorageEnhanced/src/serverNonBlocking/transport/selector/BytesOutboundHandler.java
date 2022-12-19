package serverNonBlocking.transport.selector;

import serverNonBlocking.transport.TransportException;
import serverNonBlocking.transport.WatchdogTimer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.function.Function;

public class BytesOutboundHandler implements OutboundHandler {
    public static final int BUFFER_SIZE = 8192;

    private final long operId;
    private final SelectionKey key;
    private final Function<ByteBuffer, Boolean> byteSupplierCallback;
    private final Runnable finishedCallback;
    private final ByteBuffer buffer;
    private boolean lastChunk = false;

    public BytesOutboundHandler(long operId,
                                SelectionKey key,
                                Function<ByteBuffer, Boolean> byteSupplierCallback,
                                Runnable finishedCallback) {
        this.operId = operId;
        this.key = key;
        this.byteSupplierCallback = byteSupplierCallback;
        this.finishedCallback = finishedCallback;
        buffer = ByteBuffer.wrap(new byte[BUFFER_SIZE]);
        buffer.flip();
    }

    @Override
    public void outbound() {
        WatchdogTimer.instance().resetOperationCountdown(operId);
        try {
            var channel = (SocketChannel) key.channel();

            int writeByteCount;
            do {
                if (!lastChunk && !buffer.hasRemaining()) {
                    buffer.clear();
                    lastChunk = byteSupplierCallback.apply(buffer);
                    buffer.flip();
                }

                writeByteCount = channel.write(buffer);
            }
            while (writeByteCount > 0);

            if (writeByteCount < 0) {
                throw new TransportException("unexpected channel closing");
            }

            if (lastChunk && !buffer.hasRemaining()) {
                key.interestOpsAnd(~SelectionKey.OP_WRITE);
                WatchdogTimer.instance().cancel(operId);
                finishedCallback.run();
            }
        } catch (IOException e) {
            throw new TransportException(e.getMessage(), e);
        }
    }
}
