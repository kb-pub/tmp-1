package message.serialization.quick.message;

import message.LoginRequest;
import message.serialization.quick.MessageStateSerializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class LoginRequestStateSerializer implements MessageStateSerializer<LoginRequest> {
    @Override
    public void serialize(LoginRequest message, DataOutputStream stream) throws IOException {
        writeString(message.getUser(), stream);
        writeString(message.getPassword(), stream);
    }

    @Override
    public LoginRequest deserialize(DataInputStream stream) throws IOException {
        var user = readString(stream);
        var password = readString(stream);
        return new LoginRequest(user, password);
    }
}
