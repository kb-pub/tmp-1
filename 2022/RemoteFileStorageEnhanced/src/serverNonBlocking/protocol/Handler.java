package serverNonBlocking.protocol;

import message.Message;
import serverNonBlocking.transport.Transport;

public abstract class Handler {
    protected final Transport transport;

    public Handler(Transport transport) {
        this.transport = transport;
    }

    public abstract void handle(Message request);
    public abstract void closeResources();
}
