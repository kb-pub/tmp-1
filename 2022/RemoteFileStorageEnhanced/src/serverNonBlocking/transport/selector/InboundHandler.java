package serverNonBlocking.transport.selector;

import message.serialization.SerializationException;

public interface InboundHandler {
    void inbound() throws SerializationException;
}
