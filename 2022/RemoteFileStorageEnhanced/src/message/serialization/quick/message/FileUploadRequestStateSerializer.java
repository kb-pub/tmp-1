package message.serialization.quick.message;

import message.FileUploadRequest;
import message.serialization.quick.MessageStateSerializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class FileUploadRequestStateSerializer implements MessageStateSerializer<FileUploadRequest> {
    @Override
    public void serialize(FileUploadRequest message, DataOutputStream stream) throws IOException {
        writeString(message.getToken(), stream);
        writeString(message.getFilename(), stream);
        writeLong(message.getFilesize(), stream);
    }

    @Override
    public FileUploadRequest deserialize(DataInputStream stream) throws IOException {
        var token = readString(stream);
        var filename = readString(stream);
        var filesize = readLong(stream);
        return new FileUploadRequest(token, filename, filesize);
    }
}
