package message.serialization;

import java.io.IOException;

public class SerializationException extends IOException {
    public SerializationException(String message) {
        super(message);
    }

    public SerializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
