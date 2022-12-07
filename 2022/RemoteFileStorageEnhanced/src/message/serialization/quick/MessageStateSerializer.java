package message.serialization.quick;

import message.Message;
import message.serialization.SerializationException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public interface MessageStateSerializer<T extends Message> {
    void serialize(T message, DataOutputStream stream) throws IOException;

    T deserialize(DataInputStream input) throws IOException;

    default void writeString(String text, DataOutputStream stream) throws IOException {
        text = text == null ? "" : text;
        var bytes = text.getBytes(StandardCharsets.UTF_8);
        throwIf(bytes.length > Short.MAX_VALUE, "string is too long to write");
        stream.writeShort(bytes.length);
        stream.write(bytes);
    }

    default String readString(DataInputStream stream) throws IOException {
        var length = stream.readShort();
        throwIf(length < 0, "string length < 0");
        if (length == 0) {
            return "";
        }
        var bytes = new byte[length];
        var read_count = stream.read(bytes);
        throwIf(read_count < length, "string data suddenly ends");
        return new String(bytes, StandardCharsets.UTF_8);
    }

    default void writeInt(int value, DataOutputStream stream) throws IOException {
        stream.writeInt(value);
    }

    default int readInt(DataInputStream stream) throws IOException {
        return stream.readInt();
    }

    default void writeLong(long value, DataOutputStream stream) throws IOException {
        stream.writeLong(value);
    }

    default long readLong(DataInputStream stream) throws IOException {
        return stream.readLong();
    }

    default List<String> readStringList(DataInputStream stream) throws IOException {
        int size = readInt(stream);
        throwIf(size < 0, "input list size < 0");
        var list = new ArrayList<String>();
        for (int i = 0; i < size; i++) {
            list.add(readString(stream));
        }
        return list;
    }

    default void writeStringList(List<String> list, DataOutputStream stream) throws IOException {
        list = list == null ? List.of() : list;
        writeInt(list.size(), stream);
        for (var str : list) {
            writeString(str, stream);
        }
    }

    default void throwIf(boolean condition, String message) {
        if (condition) {
            throw new SerializationException(message);
        }
    }

}
