package protocol.common.codec;

import protocol.common.message.Message;

import java.io.InputStream;
import java.io.OutputStream;

public interface Codec {
    void encode(Message message, OutputStream out);
    <T extends Message> T decode(Class<T> type, InputStream in);
}
