package server.protocol;

import exception.AppException;
import message.ErrorMessage;
import message.FileListRequest;
import message.LoginRequest;
import server.service.FileService;
import server.service.UserService;
import server.transport.Transport;

public class Protocol {
    private final Transport transport;
    private final UserService userService;
    private final FileService fileService;

    public Protocol(Transport transport, UserService userService, FileService fileService) {
        this.transport = transport;
        this.userService = userService;
        this.fileService = fileService;
    }

    public void handle() {
        try (var s = transport.getSocket()) {
            var message = transport.receive();
            System.out.println(message + " received");
            if (message instanceof LoginRequest request) {
                new LoginHandler(transport, userService).handle(request);
            } else if (message instanceof FileListRequest request) {
                new FileListHandler(transport, userService, fileService).handle(request);
            } else {
                throw new AppException("unexpected client message");
            }
        } catch (Throwable e) {
            transport.send(new ErrorMessage(e.getMessage()));
        }
    }
}
