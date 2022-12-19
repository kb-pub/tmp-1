package serverNonBlocking.protocol;

import exception.AppException;
import message.FileUploadDone;
import message.FileUploadRequest;
import message.FileUploadResponse;
import message.Message;
import serverNonBlocking.service.FileService;
import serverNonBlocking.service.UserService;
import serverNonBlocking.transport.Transport;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class FileUploadHandler extends Handler {
    private final UserService userService;
    private final FileService fileService;
    private long bytesToReceive;
    private OutputStream fileOutputStream;

    public FileUploadHandler(Transport transport, UserService userService, FileService fileService) {
        super(transport);
        this.userService = userService;
        this.fileService = fileService;
    }

    @Override
    public void handle(Message _request) {
        var request = (FileUploadRequest) _request;
        // TODO verify input!
        try {
            var user = userService.getUserForToken(request.getToken());
            bytesToReceive = request.getFilesize();
            var filepath = fileService.getFilepath(request.getFilename(), user);
            fileOutputStream = fileService.getOutputStream(filepath);
            transport.sendMessage(new FileUploadResponse(),
                    () -> transport.receiveBytes(
                            bytesToReceive,
                            this::transferBytes,
                            this::transferFinished));
        } catch (IOException e) {
            closeResources();
            throw new AppException(e.getMessage(), e);
        }
    }

    private void transferBytes(ByteBuffer buffer) {
        try {
            int byteCount = buffer.remaining();
            fileOutputStream.write(buffer.array(), 0, byteCount);
            bytesToReceive -= byteCount;
        } catch (IOException e) {
            closeResources();
            throw new AppException(e.getMessage(), e);
        }
    }

    private void transferFinished() {
        try {
            transport.sendMessage(new FileUploadDone(), transport::close);
        } finally {
            closeResources();
        }
    }

    @Override
    public void closeResources() {
        try {
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
