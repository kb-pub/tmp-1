package protocol.echo;

import protocol.common.codec.Codec;
import protocol.common.message.Message;
import protocol.common.transport.Transport;
import protocol.common.transport.TransportException;

import java.net.Socket;

public class SocketTransport implements Transport {
    private final String host;
    private final int port;
    private final Codec codec;

    private Socket socket;

    public SocketTransport(Socket socket, Codec codec) {
        host = "";
        port = 0;
        this.codec = codec;
        this.socket = socket;
    }

    public SocketTransport(String host, int port, Codec codec) {
        this.host = host;
        this.port = port;
        this.codec = codec;
    }

    @Override
    public void connect() throws TransportException {
        try {
            if (socket == null || !socket.isConnected() || socket.isClosed()) {
                socket = new Socket(host, port);
                socket.setSoTimeout(7000);
            }
        } catch (Exception e) {
            throw new TransportException(e);
        }
    }

    @Override
    public void disconnect() throws TransportException {
        try {
            if (socket != null && socket.isConnected()) {
                socket.close();
            }
        } catch (Exception e) {
            throw new TransportException(e);
        }
    }

    @Override
    public void send(Message message) throws TransportException {
        try {
            connect();
            codec.encode(message, socket.getOutputStream());
            socket.getOutputStream().flush();
        } catch (Exception e) {
            throw new TransportException(e);
        }
    }

    @Override
    public <T extends Message> T receive(Class<T> type) throws TransportException {
        try {
            return codec.decode(type, socket.getInputStream());
        } catch (Exception e) {
            throw new TransportException(e);
        }
    }

    @Override
    public String toString() {
        return socket != null ? socket.toString() : "null";
    }
}
