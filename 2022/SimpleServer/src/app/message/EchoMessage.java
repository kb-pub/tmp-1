package app.message;

import java.io.Serial;
import java.io.Serializable;

public class EchoMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1234567L;
    private final String text;

    public EchoMessage(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "EchoMessage{" +
                "text='" + text + '\'' +
                '}';
    }
}
