package protocol.echo;

import protocol.common.codec.Codec;
import protocol.common.codec.CodecException;
import protocol.common.message.Message;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class OneLineCodec implements Codec {
    @Override
    public void encode(Message message, OutputStream out) {
        String text;
        if (message instanceof EchoRequest request) {
            text = request.getText();
        } else if (message instanceof EchoResponse response) {
            text = response.getText();
        } else {
            throw new CodecException("unexpected type: " + message.getClass());
        }
        if (text == null) {
            throw new CodecException("message must be not null");
        }
        try {
            text += "\n";
            out.write(text.getBytes(StandardCharsets.UTF_8));
            out.flush();
        } catch (IOException e) {
            throw new CodecException(e);
        }
    }

    @Override
    public <T extends Message> T decode(Class<T> type, InputStream in) {
        try {
            var reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            var text = reader.readLine();
            if (type == EchoRequest.class) {
                return type.cast(new EchoRequest(text));
            } else if (type == EchoResponse.class) {
                return type.cast(new EchoResponse(text));
            } else {
                throw new CodecException("unexpected type: " + type);
            }
        } catch (Exception e) {
            throw new CodecException(e);
        }
    }
}
