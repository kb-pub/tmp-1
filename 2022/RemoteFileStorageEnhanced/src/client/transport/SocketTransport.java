package client.transport;

import exception.AppException;
import message.Message;
import message.serialization.MessageSerializer;
import message.serialization.MessageSerializerFactory;
import settings.Settings;
import util.Util;

import java.io.*;
import java.net.Socket;

class SocketTransport implements Transport {
    private final Socket socket;
    private final MessageSerializer messageSerializer;

    SocketTransport() {
        try {
            socket = new Socket(Settings.SERVER_ADDRESS, Settings.SERVER_PORT);
            messageSerializer = MessageSerializerFactory.get();
        } catch (IOException e) {
            throw new AppException(e.getMessage(), e);
        }
    }

    @Override
    public void close() {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (Exception e) {
            throw new AppException(e.getMessage(), e);
        }
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
            throw new AppException(e.getMessage(), e);
        }
    }

    @Override
    public OutputStream getOutputStream() {
        return Util.rethrow(socket::getOutputStream);
    }
}
