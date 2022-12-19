package test;

import message.ErrorMessage;
import message.serialization.serialized.SerializedMessageSerializer;

public class SerializedMessageSerializerTest {
    public static void main(String[] args) throws Exception {
        new SerializedMessageSerializerTest().test();
    }

    void test() throws Exception {
        var serializer = new SerializedMessageSerializer();
        var message = new ErrorMessage("test message");
        var bytes = serializer.serialize(message);
        var message2 = serializer.deserialize(bytes);
        if (!message.equals(message2)) {
            throw new RuntimeException("fail");
        }
    }
}
