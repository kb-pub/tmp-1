package message.serialization.quick.message;

import message.ErrorMessage;
import message.serialization.quick.MessageStateSerializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ErrorMessageStateSerializer implements MessageStateSerializer<ErrorMessage> {
    @Override
    public void serialize(ErrorMessage message, DataOutputStream stream) throws IOException {
        writeString(message.getMessage(), stream);
    }

    @Override
    public ErrorMessage deserialize(DataInputStream input) throws IOException {
        var errorDescription = readString(input);
        return new ErrorMessage(errorDescription);
    }
}
