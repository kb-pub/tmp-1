package client.transport;

import exception.AppException;
import message.Message;
import message.serialization.MessageSerializer;
import message.serialization.MessageSerializerFactory;
import settings.Settings;

import java.io.*;
import java.net.Socket;

import static util.Util.rethrow;
import static util.Util.throwIf;

class SocketTransport implements Transport {
    private final Socket socket;
    private final MessageSerializer messageSerializer;

    SocketTransport() {
        socket = rethrow(() -> new Socket(Settings.SERVER_ADDRESS, Settings.SERVER_PORT));
        messageSerializer = MessageSerializerFactory.get();
    }

    @Override
    public void close() {
        rethrow(() -> {
            if (socket != null)
                socket.close();
        });
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
            throwIf(dataStream.read(bytes) < length,
                    "unexpected end of input data chunk");
            return messageSerializer.deserialize(bytes);
        } catch (IOException e) {
            throw new AppException(e.getMessage(), e);
        }
    }

    @Override
    public OutputStream getOutputStream() {
        return rethrow(socket::getOutputStream);
    }

    @Override
    public InputStream getInputStream() {
        return rethrow(socket::getInputStream);
    }
}
