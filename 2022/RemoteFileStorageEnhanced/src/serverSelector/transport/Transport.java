package serverSelector.transport;

import message.Message;

import java.io.Closeable;
import java.nio.ByteBuffer;
import java.util.function.Consumer;

public interface Transport extends Closeable {
    void send(Message message, Runnable callback);

    void receive(Consumer<Message> callback);

    void receiveBytes(long count, Consumer<ByteBuffer> callback);

    void inbound();

    void outbound();

    @Override
    void close();
}
