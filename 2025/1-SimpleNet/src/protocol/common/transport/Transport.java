package protocol.common.transport;

import protocol.common.message.Message;

public interface Transport {
    void connect() throws TransportException;
    void disconnect() throws TransportException;
    void send(Message message) throws TransportException;
    <T extends Message> T receive(Class<T> type) throws TransportException;
}
