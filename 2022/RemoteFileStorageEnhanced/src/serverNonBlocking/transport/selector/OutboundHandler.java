package serverNonBlocking.transport.selector;

import message.serialization.SerializationException;

public interface OutboundHandler {
    void outbound() throws SerializationException;
}
