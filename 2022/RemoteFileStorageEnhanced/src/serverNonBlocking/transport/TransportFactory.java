package serverNonBlocking.transport;

import message.serialization.MessageSerializerFactory;
import serverNonBlocking.transport.selector.SelectorTransport;

import java.nio.channels.SelectionKey;

public class TransportFactory {
    public static Transport get(SelectionKey key) {
        return new SelectorTransport(key, MessageSerializerFactory.get());
    }
}
