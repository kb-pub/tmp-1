package serverSelector.protocol;

import message.FileListRequest;
import message.FileListResponse;
import serverSelector.protocol.Handler;
import serverSelector.service.FileService;
import serverSelector.service.UserService;
import serverSelector.transport.Transport;

public class FileListHandler extends Handler {
    private final UserService userService;
    private final FileService fileService;

    public FileListHandler(Transport transport, UserService userService, FileService fileService) {
        super(transport);
        this.userService = userService;
        this.fileService = fileService;
    }

    public void handle(FileListRequest request) {
        var user = userService.getUserForToken(request.getToken());
        var files = fileService.getFilesForUser(user);
        transport.send(new FileListResponse(files), transport::close);
    }
}
