package message.serialization.quick.message;

import message.LoginResponse;
import message.serialization.quick.MessageStateSerializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class LoginResponseStateSerializer implements MessageStateSerializer<LoginResponse> {
    @Override
    public void serialize(LoginResponse message, DataOutputStream stream) throws IOException {
        writeString(message.getToken(), stream);
    }

    @Override
    public LoginResponse deserialize(DataInputStream stream) throws IOException {
        var token = readString(stream);
        return new LoginResponse(token);
    }
}
