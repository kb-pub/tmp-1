package message.serialization.quick.message;

import message.FileDeleteRequest;
import message.serialization.quick.MessageStateSerializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class FileDeleteRequestStateSerializer implements MessageStateSerializer<FileDeleteRequest> {
    @Override
    public void serialize(FileDeleteRequest message, DataOutputStream stream) throws IOException {
        writeString(message.getToken(), stream);
        writeString(message.getFilename(), stream);
    }

    @Override
    public FileDeleteRequest deserialize(DataInputStream stream) throws IOException {
        var token = readString(stream);
        var filename = readString(stream);
        return new FileDeleteRequest(token, filename);
    }
}
