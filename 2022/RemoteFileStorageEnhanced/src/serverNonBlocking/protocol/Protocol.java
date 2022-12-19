package serverNonBlocking.protocol;

import exception.AppException;
import message.*;
import serverNonBlocking.service.FileService;
import serverNonBlocking.service.UserService;
import serverNonBlocking.transport.Transport;

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
        Handler handler = null;
        try {
            handler = switch (message) {
                case LoginRequest req -> new LoginHandler(transport, userService);
                case FileListRequest req -> new FileListHandler(transport, userService, fileService);
                case FileUploadRequest req -> new FileUploadHandler(transport, userService, fileService);
                case FileDownloadRequest req -> new FileDownloadHandler(transport, userService, fileService);
                case FileDeleteRequest req -> new FileDeleteHandler(transport, userService, fileService);
                default -> throw new AppException("unexpected client message");
            };
            handler.handle(message);
        } catch (AppException e) {
            try {
                if (handler != null) {
                    handler.closeResources();
                }
                transport.sendMessage(new ErrorMessage(e.getMessage()), transport::close);
            } catch (Throwable t) {
                transport.close();
            }
        }
    }
}
