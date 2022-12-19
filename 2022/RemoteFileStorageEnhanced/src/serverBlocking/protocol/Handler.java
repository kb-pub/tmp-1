package serverBlocking.protocol;

import serverBlocking.transport.Transport;

abstract class Handler {
    protected final Transport transport;

    public Handler(Transport transport) {
        this.transport = transport;
    }
}
