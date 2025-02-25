package protocol.echo;

import protocol.common.message.Message;

public class EchoRequest extends Message {
    private final String text;

    public EchoRequest(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "EchoRequest{" +
                "text='" + text + '\'' +
                '}';
    }
}
