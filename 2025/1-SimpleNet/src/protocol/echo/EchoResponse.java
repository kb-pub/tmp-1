package protocol.echo;

import protocol.common.message.Message;

public class EchoResponse extends Message {
    private final String text;

    public EchoResponse(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "EchoResponse{" +
                "text='" + text + '\'' +
                '}';
    }
}
