package client.protocol;

import client.transport.TransportFactory;
import message.FileListRequest;
import message.FileListResponse;

import java.util.List;

public class FileListRemoteOperation extends RemoteOperation {
    public List<String> get(String token) {
        try (var transport = TransportFactory.connect()) {
            transport.send(new FileListRequest(token));
            var message = extractMessageOrFail(transport.receive(), FileListResponse.class);
            return message.getFiles();
        }
    }
}
