package client.protocol;

import client.transport.TransportFactory;
import message.FileListRequest;
import message.FileListResponse;

import java.util.List;

import static util.Util.rethrow;

public class FileListRemoteOperation extends RemoteOperation {
    public List<String> get(String token) {
        try (var transport = TransportFactory.connect()) {
            rethrow(() -> Thread.sleep(10000));
            transport.send(new FileListRequest(token));
            var message = extractMessageOrFail(transport.receive(), FileListResponse.class);
            return message.getFiles();
        }
    }
}
