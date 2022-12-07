package message.serialization.serialized;

import exception.AppException;
import message.Message;
import message.serialization.MessageSerializer;

import java.io.*;

public class SerializedMessageSerializer implements MessageSerializer {
    @Override
    public byte[] serialize(Message message) {
        try {
            var out = new ByteArrayOutputStream();
            new ObjectOutputStream(out).writeObject(message);
            return out.toByteArray();
        } catch (IOException e) {
            throw new AppException(e.getMessage(), e);
        }
    }

    @Override
    public Message deserialize(byte[] bytes) {
        try {
            return (Message) new ObjectInputStream(
                    new ByteArrayInputStream(bytes)).readObject();
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            throw new AppException(e.getMessage(), e);
        }
    }
}
