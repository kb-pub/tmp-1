package client.transport;

import exception.AppException;
import settings.Settings;

public class TransportFactory {
    public static Transport connect() {
        var type = Settings.CLIENT_TRANSPORT;
        var transport = switch (type) {
            case "socket" -> new SocketTransport();
            case "socket_channel" -> new SocketChannelTransport();
            default -> throw new AppException(
                    "internal error: no transport realization for type " + type);
        };
        System.out.println("used transport: " + transport);
        return transport;
    }
}
