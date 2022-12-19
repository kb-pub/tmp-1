package client.transport;

import message.Message;

import java.io.InputStream;
import java.io.OutputStream;

public interface Transport extends AutoCloseable {
    void close();

    void send(Message message);

    Message receive();

    OutputStream getOutputStream();
    InputStream getInputStream();
}
