package client.protocol;

import client.transport.TransportFactory;
import message.FileUploadDone;
import message.FileUploadRequest;
import message.FileUploadResponse;

import java.io.InputStream;

import static util.Util.rethrow;

public class FileUploadRemoteOperation extends RemoteOperation {
    public void upload(String token, String filename, long filesize, InputStream input) {
        try (var transport = TransportFactory.connect()) {
            transport.send(new FileUploadRequest(token, filename, filesize));
            extractMessageOrFail(transport.receive(), FileUploadResponse.class);
            rethrow(() -> input.transferTo(transport.getOutputStream()));
            extractMessageOrFail(transport.receive(), FileUploadDone.class);
        }
    }
}
