package server.transport;

import message.serialization.MessageSerializerFactory;

import java.net.Socket;

public class TransportFactory {
    public static Transport get(Socket socket) {
        return new SocketTransport(socket, MessageSerializerFactory.get());
    }
}
