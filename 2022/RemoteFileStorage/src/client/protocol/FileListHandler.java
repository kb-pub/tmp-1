package client.protocol;

import client.transport.Transport;
import exception.AppException;
import message.ErrorMessage;
import message.FileListRequest;
import message.FileListResponse;

import java.util.List;

public class FileListHandler extends Handler {
    public FileListHandler(Transport transport) {
        super(transport);
    }

    public List<String> get(String token) {
        transport.send(new FileListRequest(token));
        var message = transport.receive();
        if (message instanceof FileListResponse response) {
            return response.getFiles();
        }
        else if (message instanceof ErrorMessage error) {
            throw new AppException(error.getMessage());
        }
        else {
            throw new AppException("unexpected message: " + message);
        }
    }
}
