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

import static util.Util.rethrow;
import static util.Util.throwIf;

class SocketChannelTransport implements Transport {
    private final SocketChannel channel;
    private final MessageSerializer messageSerializer;

    SocketChannelTransport() {
        channel = rethrow(() -> SocketChannel.open(new InetSocketAddress(
                Settings.SERVER_ADDRESS, Settings.SERVER_PORT)));
        messageSerializer = MessageSerializerFactory.get();
    }

    @Override
    public void close() {
        rethrow(() -> {
            if (channel != null) {
                channel.close();
            }
        });
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
            throwIf(dataStream.read(bytes) < length,
                    "unexpected end of input data chunk");
            return messageSerializer.deserialize(bytes);
        } catch (IOException e) {
            throw new AppException(e.getMessage(), e);
        }
    }

    @Override
    public OutputStream getOutputStream() {
        return rethrow(() -> channel.socket().getOutputStream());
    }

    @Override
    public InputStream getInputStream() {
        return rethrow(() -> channel.socket().getInputStream());
    }
}
