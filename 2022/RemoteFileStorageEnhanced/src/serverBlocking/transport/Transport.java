package serverBlocking.transport;

import message.Message;

import java.io.InputStream;
import java.net.Socket;

public interface Transport {
    Socket getSocket();

    void send(Message message);

    Message receive();

    InputStream getInputStream();
}
