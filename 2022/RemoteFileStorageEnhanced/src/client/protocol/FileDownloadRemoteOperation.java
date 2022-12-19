package client.protocol;

import client.transport.TransportFactory;
import exception.AppException;
import message.FileDownloadDone;
import message.FileDownloadRequest;
import message.FileDownloadResponse;

import java.io.BufferedInputStream;
import java.io.OutputStream;

import static util.Util.rethrow;
import static util.Util.throwIf;

public class FileDownloadRemoteOperation extends RemoteOperation {
    private static final int BUFFER_SIZE = 8192;

    public void download(String token, String filename, OutputStream output) {
        try (var transport = TransportFactory.connect()) {
            transport.send(new FileDownloadRequest(token, filename));
            var response = extractMessageOrFail(transport.receive(), FileDownloadResponse.class);
            var filesize = response.getFilesize();
            rethrow(() -> {
                var input = transport.getInputStream();
                long bytesRemaining = filesize;
                var buffer = new byte[BUFFER_SIZE];
                while (bytesRemaining > 0) {
                    int chunkSize = (int) Long.min(bytesRemaining, BUFFER_SIZE);
                    var readCount = input.read(buffer, 0, chunkSize);
                    output.write(buffer, 0, readCount);
                    bytesRemaining -= readCount;
                }
            });
            extractMessageOrFail(transport.receive(), FileDownloadDone.class);
        }
    }
}
