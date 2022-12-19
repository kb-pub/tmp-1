package message.serialization;

import message.Message;

public interface MessageSerializer {
    byte[] serialize(Message message) throws SerializationException;

    Message deserialize(byte[] bytes) throws SerializationException;
}
