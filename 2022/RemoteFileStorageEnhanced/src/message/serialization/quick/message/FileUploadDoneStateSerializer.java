package message.serialization.quick.message;

import message.FileUploadDone;
import message.serialization.quick.MessageStateSerializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class FileUploadDoneStateSerializer implements MessageStateSerializer<FileUploadDone> {
    @Override
    public void serialize(FileUploadDone message, DataOutputStream stream) throws IOException {

    }

    @Override
    public FileUploadDone deserialize(DataInputStream stream) throws IOException {
        return new FileUploadDone();
    }
}
