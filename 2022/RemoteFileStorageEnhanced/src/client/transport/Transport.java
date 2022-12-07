package client.transport;

import message.Message;

import java.io.OutputStream;

public interface Transport extends AutoCloseable {
    void close();

    void send(Message message);

    Message receive();

    OutputStream getOutputStream();
}
