package message.serialization.quick.message;

import message.FileListResponse;
import message.serialization.quick.MessageStateSerializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class FileListResponseStateSerializer implements MessageStateSerializer<FileListResponse> {
    @Override
    public void serialize(FileListResponse message, DataOutputStream stream) throws IOException {
        writeStringList(message.getFiles(), stream);
    }

    @Override
    public FileListResponse deserialize(DataInputStream stream) throws IOException {
        var files = readStringList(stream);
        return new FileListResponse(files);
    }
}
