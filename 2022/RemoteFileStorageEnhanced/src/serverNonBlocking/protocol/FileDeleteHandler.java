package serverNonBlocking.protocol;

import message.FileDeleteRequest;
import message.FileDeleteResponse;
import message.Message;
import serverNonBlocking.service.FileService;
import serverNonBlocking.service.UserService;
import serverNonBlocking.transport.Transport;

import static util.Util.rethrow;
import static util.Util.throwIf;

public class FileDeleteHandler extends Handler {
    private final UserService userService;
    private final FileService fileService;

    public FileDeleteHandler(Transport transport, UserService userService, FileService fileService) {
        super(transport);
        this.userService = userService;
        this.fileService = fileService;
    }

    @Override
    public void handle(Message _request) {
        var request = (FileDeleteRequest) _request;
        var user = userService.getUserForToken(request.getToken());
        var filepath = fileService.getFilepath(request.getFilename(), user);
        throwIf(!fileService.exists(filepath), "no file found");
        rethrow(() -> fileService.delete(filepath));
        transport.sendMessage(new FileDeleteResponse(), transport::close);
    }

    @Override
    public void closeResources() {
        // nothing to close
    }
}
