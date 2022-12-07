package message.serialization;

import message.serialization.quick.QuickMessageSerializer;
import message.serialization.serialized.SerializedMessageSerializer;
import settings.Settings;

public class MessageSerializerFactory {
    public static MessageSerializer get() {
        var method = Settings.MESSAGE_SERIALIZATION_METHOD;
        return switch (method) {
            case "serialized" -> new SerializedMessageSerializer();
            case "quick" -> new QuickMessageSerializer();
            default -> throw new SerializationException("no serialization method: " + method);
        };
    }
}
