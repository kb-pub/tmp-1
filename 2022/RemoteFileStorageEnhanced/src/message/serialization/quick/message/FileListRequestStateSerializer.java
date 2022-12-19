package message.serialization.quick.message;

import message.FileListRequest;
import message.serialization.quick.MessageStateSerializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class FileListRequestStateSerializer implements MessageStateSerializer<FileListRequest> {
    @Override
    public void serialize(FileListRequest message, DataOutputStream stream) throws IOException {
        writeString(message.getToken(), stream);
    }

    @Override
    public FileListRequest deserialize(DataInputStream stream) throws IOException {
        var token = readString(stream);
        return new FileListRequest(token);
    }
}
