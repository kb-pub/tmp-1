package client.protocol;

import client.transport.TransportFactory;
import message.FileDeleteRequest;
import message.FileDeleteResponse;
import message.FileListRequest;
import message.FileListResponse;

import java.util.List;

public class FileDeleteRemoteOperation extends RemoteOperation {
    public void delete(String token, String filename) {
        try (var transport = TransportFactory.connect()) {
            transport.send(new FileDeleteRequest(token, filename));
            extractMessageOrFail(transport.receive(), FileDeleteResponse.class);
        }
    }
}
