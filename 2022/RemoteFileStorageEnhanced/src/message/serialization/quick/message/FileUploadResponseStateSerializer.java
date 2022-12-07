package message.serialization.quick.message;

import message.FileUploadResponse;
import message.serialization.quick.MessageStateSerializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class FileUploadResponseStateSerializer implements MessageStateSerializer<FileUploadResponse> {
    @Override
    public void serialize(FileUploadResponse message, DataOutputStream stream) throws IOException {

    }

    @Override
    public FileUploadResponse deserialize(DataInputStream stream) throws IOException {
        return new FileUploadResponse();
    }
}
