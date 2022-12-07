package serverSelector.protocol;

import exception.AppException;
import message.*;
import serverSelector.transport.Transport;
import serverSelector.service.FileService;
import serverSelector.service.UserService;

public class Protocol {
    private final Transport transport;
    private final UserService userService;
    private final FileService fileService;

    public Protocol(Transport transport, UserService userService, FileService fileService) {
        this.transport = transport;
        this.userService = userService;
        this.fileService = fileService;
    }

    public void handle(Message message) {
            try {
                System.out.println(message + " received");
                if (message instanceof LoginRequest request) {
                    new LoginHandler(transport, userService).handle(request);
                } else if (message instanceof FileListRequest request) {
                    new FileListHandler(transport, userService, fileService).handle(request);
                } else if (message instanceof FileUploadRequest request) {
                    new FileUploadHandler(transport, userService, fileService).handle(request);
                } else {
                    throw new AppException("unexpected client message");
                }
            } catch (AppException e) {
                transport.send(new ErrorMessage(e.getMessage()), transport::close);
            }
    }
}
