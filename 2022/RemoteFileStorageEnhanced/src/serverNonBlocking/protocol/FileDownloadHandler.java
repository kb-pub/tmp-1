package serverNonBlocking.protocol;

import exception.AppException;
import message.FileDownloadDone;
import message.FileDownloadRequest;
import message.FileDownloadResponse;
import message.Message;
import serverNonBlocking.service.FileService;
import serverNonBlocking.service.UserService;
import serverNonBlocking.transport.Transport;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileDownloadHandler extends Handler {
    private final UserService userService;
    private final FileService fileService;
    private FileChannel fileChannel;

    public FileDownloadHandler(Transport transport, UserService userService, FileService fileService) {
        super(transport);
        this.userService = userService;
        this.fileService = fileService;
    }

    @Override
    public void handle(Message _request) {
        var request = (FileDownloadRequest) _request;
        try {
            var user = userService.getUserForToken(request.getToken());
            var filepath = fileService.getFilepath(request.getFilename(), user);
            if (!fileService.exists(filepath)) {
                throw new AppException("no file found: '" + request.getFilename() + "'");
            }
            fileChannel = fileService.getReadChannel(filepath);
            var filesize = fileService.getFilesize(filepath);
            transport.sendMessage(new FileDownloadResponse(filesize),
                    () -> transport.sendBytes(
                            this::transferBytes,
                            this::finishFileSending));
        } catch (IOException e) {
            throw new AppException(e.getMessage(), e);
        }
    }

    private boolean transferBytes(ByteBuffer buffer) {
        try {
            var byteCount = fileChannel.read(buffer);
            return byteCount < 0;
        } catch (Exception e) {
            closeResources();
            throw new AppException(e.getMessage(), e);
        }
    }

    private void finishFileSending() {
        try {
            transport.sendMessage(new FileDownloadDone(), transport::close);
        } finally {
            closeResources();
        }
    }

    @Override
    public void closeResources() {
        if (fileChannel != null) {
            try {
                fileChannel.close();
            } catch (IOException e) {
                throw new AppException(e.getMessage(), e);
            }
        }
    }
}
