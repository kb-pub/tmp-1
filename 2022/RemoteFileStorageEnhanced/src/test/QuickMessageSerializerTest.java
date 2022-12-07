package test;

import message.ErrorMessage;
import message.serialization.quick.QuickMessageSerializer;

public class QuickMessageSerializerTest {
    public static void main(String[] args) {
        new QuickMessageSerializerTest().test();
    }

    void test() {
        var serializer = new QuickMessageSerializer();
        var message = new ErrorMessage("test message");
        var bytes = serializer.serialize(message);
        var message2 = serializer.deserialize(bytes);
        if (!message.equals(message2)) {
            throw new RuntimeException("fail");
        }
    }
}
