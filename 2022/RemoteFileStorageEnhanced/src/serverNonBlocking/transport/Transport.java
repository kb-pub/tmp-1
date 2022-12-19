package serverNonBlocking.transport;

import message.Message;

import java.io.Closeable;
import java.nio.ByteBuffer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Main interface to manage asynchronous data transfer.
 * <br />
 * Drives intermediate level in chaining Selector - Transport - {@link serverNonBlocking.protocol.Protocol}. Receiving
 * {@link Message}s or raw bytes from upper Protocol level and sending it to network
 * via selector and vice versa.
 * <br />
 * Upper level {@link serverNonBlocking.protocol.Protocol} should use {@link #sendMessage}
 * and {@link #sendBytes} methods to sending data and receiveMessage() and receiveBytes()
 * to receiving data. Because of all operations are asynchronous there are callbacks used to
 * call when operation is complete (or can continue to transfer in case of raw bytes transfer).
 * <br />
 * Lower level - Selector - should call {@link #inbound()} when new data able to read and
 * {@link #outbound()} when network is ready to consume portion of data.
 */
public interface Transport extends Closeable {
    void sendMessage(Message message, Runnable finishedCallback);

    void sendBytes(Function<ByteBuffer, Boolean> continueCallback,
                   Runnable finishedCallback);

    void receiveMessage(Consumer<Message> finishedCallback);

    void receiveBytes(long count,
                      Consumer<ByteBuffer> continueCallback,
                      Runnable finishedCallback);

    void inbound();

    void outbound();

    @Override
    void close();
}
