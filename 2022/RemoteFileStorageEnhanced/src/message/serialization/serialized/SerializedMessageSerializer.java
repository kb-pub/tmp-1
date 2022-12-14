package message.serialization.serialized;

import exception.AppException;
import message.Message;
import message.serialization.MessageSerializer;
import message.serialization.SerializationException;

import java.io.*;

public class SerializedMessageSerializer implements MessageSerializer {
    @Override
    public byte[] serialize(Message message) throws SerializationException {
        try {
            var out = new ByteArrayOutputStream();
            new ObjectOutputStream(out).writeObject(message);
            return out.toByteArray();
        } catch (IOException e) {
            throw new SerializationException(e.getMessage(), e);
        }
    }

    @Override
    public Message deserialize(byte[] bytes) throws SerializationException {
        try {
            return (Message) new ObjectInputStream(
                    new ByteArrayInputStream(bytes)).readObject();
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            throw new SerializationException(e.getMessage(), e);
        }
    }
}
