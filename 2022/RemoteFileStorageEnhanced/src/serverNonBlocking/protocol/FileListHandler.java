package serverNonBlocking.protocol;

import message.FileListRequest;
import message.FileListResponse;
import message.Message;
import serverNonBlocking.service.FileService;
import serverNonBlocking.service.UserService;
import serverNonBlocking.transport.Transport;

public class FileListHandler extends Handler {
    private final UserService userService;
    private final FileService fileService;

    public FileListHandler(Transport transport, UserService userService, FileService fileService) {
        super(transport);
        this.userService = userService;
        this.fileService = fileService;
    }

    @Override
    public void handle(Message _request) {
        var request = (FileListRequest) _request;
        var user = userService.getUserForToken(request.getToken());
        var files = fileService.getFilesForUser(user);
        transport.sendMessage(new FileListResponse(files), transport::close);
    }

    @Override
    public void closeResources() {
        // nothing to close
    }
}
