package serverNonBlocking.protocol;

import message.LoginRequest;
import message.LoginResponse;
import message.Message;
import serverNonBlocking.service.UserService;
import serverNonBlocking.transport.Transport;

public class LoginHandler extends Handler {
    private final UserService userService;

    public LoginHandler(Transport transport, UserService userService) {
        super(transport);
        this.userService = userService;
    }

    @Override
    public void handle(Message _request) {
        var request = (LoginRequest) _request;
        userService.checkUserCredentials(request.getUser(), request.getPassword());
        var token = userService.getTokenForUser(request.getUser());
        transport.sendMessage(new LoginResponse(token), transport::close);
    }

    @Override
    public void closeResources() {
        // nothing to close
    }
}
