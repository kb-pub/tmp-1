package message.serialization;

import message.Message;

public interface MessageSerializer {
    byte[] serialize(Message message);

    Message deserialize(byte[] bytes);
}
