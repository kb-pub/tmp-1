package serverSelector.protocol;

import exception.AppException;
import message.FileUploadDone;
import message.FileUploadRequest;
import message.FileUploadResponse;
import serverSelector.service.FileService;
import serverSelector.service.UserService;
import serverSelector.transport.Transport;
import util.Util;

public class FileUploadHandler extends Handler {
    private final UserService userService;
    private final FileService fileService;

    public FileUploadHandler(Transport transport, UserService userService, FileService fileService) {
        super(transport);
        this.userService = userService;
        this.fileService = fileService;
    }

    public void handle(FileUploadRequest request) {
        throw new AppException("not implemented");
        // TODO verify input!
//        var user = userService.getUserForToken(request.getToken());
//        transport.send(new FileUploadResponse());
//        var filepath = fileService.getFilepath(request.getFilename(), user);
//        fileService.withOutputStream(filepath, output -> {
//            var input = transport.getInputStream();
//            Util.rethrow(() -> {
//                for (int i = 0; i < request.getFilesize(); i++) {
//                    output.write((byte) (input.read()));
//                }
//                return true;
//            });
//        });
//        transport.send(new FileUploadDone());
    }
}
