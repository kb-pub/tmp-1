package main.message;

import java.nio.charset.StandardCharsets;

public class SimpleTextMessage extends Message {
    private final String msg;
    private final TextBody body;

    public SimpleTextMessage(String msg, String ... tags) {
        super("text", tags);
        this.msg = msg;
        body = new TextBody();
    }

    @Override
    public Body getBody() {
        return body;
    }

    public class TextBody implements Message.Body {
        private byte[] bytes;

        @Override
        public Object getPayload() {
            return msg;
        }

        @Override
        public int getSize() {
            fillBytesIfEmpty();
            return bytes.length;
        }

        @Override
        public byte[] getBytes() {
            fillBytesIfEmpty();
            return new byte[0];
        }

        private void fillBytesIfEmpty() {
            if (bytes == null)
                bytes = msg.getBytes(StandardCharsets.UTF_8);
        }
    }
}
