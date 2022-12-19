package message.serialization.quick.message;

import message.FileDownloadResponse;
import message.serialization.quick.MessageStateSerializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class FileDownloadResponseStateSerializer implements MessageStateSerializer<FileDownloadResponse> {
    @Override
    public void serialize(FileDownloadResponse message, DataOutputStream stream) throws IOException {
        writeLong(message.getFilesize(), stream);
    }

    @Override
    public FileDownloadResponse deserialize(DataInputStream stream) throws IOException {
        var filesize = readLong(stream);
        return new FileDownloadResponse(filesize);
    }
}
