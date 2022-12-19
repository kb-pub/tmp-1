package message.serialization.quick.message;

import message.FileDownloadRequest;
import message.serialization.quick.MessageStateSerializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class FileDownloadRequestStateSerializer implements MessageStateSerializer<FileDownloadRequest> {
    @Override
    public void serialize(FileDownloadRequest message, DataOutputStream stream) throws IOException {
        writeString(message.getToken(), stream);
        writeString(message.getFilename(), stream);
    }

    @Override
    public FileDownloadRequest deserialize(DataInputStream stream) throws IOException {
        var token = readString(stream);
        var filename = readString(stream);
        return new FileDownloadRequest(token, filename);
    }
}
