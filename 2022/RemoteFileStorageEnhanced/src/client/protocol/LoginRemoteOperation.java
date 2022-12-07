package client.protocol;

import client.transport.TransportFactory;
import message.LoginRequest;
import message.LoginResponse;

public class LoginRemoteOperation extends RemoteOperation {
    public String login(String user, String password) {
        try (var transport = TransportFactory.connect()) {
            transport.send(new LoginRequest(user, password));
            var message = transport.receive();
            return extractMessageOrFail(message, LoginResponse.class).getToken();
        }
    }
}
