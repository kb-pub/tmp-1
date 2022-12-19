package message.serialization.quick.message;

import message.FileDownloadDone;
import message.serialization.quick.MessageStateSerializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class FileDownloadDoneStateSerializer implements MessageStateSerializer<FileDownloadDone> {
    @Override
    public void serialize(FileDownloadDone message, DataOutputStream stream) throws IOException {

    }

    @Override
    public FileDownloadDone deserialize(DataInputStream stream) throws IOException {
        return new FileDownloadDone();
    }
}
