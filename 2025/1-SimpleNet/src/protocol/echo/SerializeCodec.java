package protocol.echo;

import protocol.common.codec.Codec;
import protocol.common.codec.CodecException;
import protocol.common.message.Message;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class SerializeCodec implements Codec {
    @Override
    public void encode(Message message, OutputStream out) {
        try {
            new ObjectOutputStream(out).writeObject(message);
        } catch (Exception e) {
            throw new CodecException(e);
        }
    }

    @Override
    public <T extends Message> T decode(Class<T> type, InputStream in) {
        try {
            return type.cast(new ObjectInputStream(in).readObject());
        } catch (Exception e) {
            throw new CodecException(e);
        }
    }
}
