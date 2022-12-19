package message.serialization.quick.message;

import message.FileDeleteResponse;
import message.serialization.quick.MessageStateSerializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class FileDeleteResponseStateSerializer implements MessageStateSerializer<FileDeleteResponse> {
    @Override
    public void serialize(FileDeleteResponse message, DataOutputStream stream) throws IOException {

    }

    @Override
    public FileDeleteResponse deserialize(DataInputStream stream) throws IOException {
        return new FileDeleteResponse();
    }
}
