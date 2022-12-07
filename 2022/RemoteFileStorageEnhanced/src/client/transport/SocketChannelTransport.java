package client.transport;

import exception.AppException;
import message.Message;
import message.serialization.MessageSerializer;
import message.serialization.MessageSerializerFactory;
import settings.Settings;
import util.Util;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

class SocketChannelTransport implements Transport {
    private final SocketChannel channel;
    private final MessageSerializer messageSerializer;

    SocketChannelTransport() {
        try {
            channel = SocketChannel.open(new InetSocketAddress(
                    Settings.SERVER_ADDRESS, Settings.SERVER_PORT));
            messageSerializer = MessageSerializerFactory.get();
        } catch (IOException e) {
            throw new AppException(e.getMessage(), e);
        }
    }

    @Override
    public void close() {
        try {
            if (channel != null) {
                channel.close();
            }
        } catch (Exception e) {
            throw new AppException(e.getMessage(), e);
        }
    }

    @Override
    public void send(Message message) {
        try {
            var dataStream = new DataOutputStream(channel.socket().getOutputStream());
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
                    new BufferedInputStream(channel.socket().getInputStream()));
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
        return Util.rethrow(() -> channel.socket().getOutputStream());
    }
}
