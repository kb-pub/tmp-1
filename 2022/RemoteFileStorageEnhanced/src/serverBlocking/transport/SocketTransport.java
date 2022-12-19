package serverBlocking.transport;

import exception.AppException;
import message.Message;
import message.serialization.MessageSerializer;
import util.Util;

import java.io.*;
import java.net.Socket;

public class SocketTransport implements Transport {
    private final Socket socket;
    private final MessageSerializer messageSerializer;

    public SocketTransport(Socket socket, MessageSerializer messageSerializer) {
        this.socket = socket;
        this.messageSerializer = messageSerializer;
    }

    @Override
    public Socket getSocket() {
        return socket;
    }

    @Override
    public void send(Message message) {
        try {
            var dataStream = new DataOutputStream(socket.getOutputStream());
            var bytes = messageSerializer.serialize(message);
            dataStream.writeInt(bytes.length);
            dataStream.write(bytes);
            dataStream.flush();
        } catch (IOException e) {
            throw new AppException(e.getMessage(), e);
        }
    }

    @Override
    public Message receive() {
        try {
            var dataStream = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));
            int length = dataStream.readInt();
            var bytes = new byte[length];
            if (dataStream.read(bytes) < length) {
                throw new AppException("unexpected end of input data chunk");
            }
            return messageSerializer.deserialize(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            throw new AppException(e.getMessage(), e);
        }
    }

    @Override
    public InputStream getInputStream() {
        return Util.rethrow(socket::getInputStream);
    }
}
