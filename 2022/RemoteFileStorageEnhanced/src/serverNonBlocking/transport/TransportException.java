package serverNonBlocking.transport;

import exception.AppException;

public class TransportException extends AppException {
    public TransportException(String message) {
        super(message);
    }

    public TransportException(String message, Throwable cause) {
        super(message, cause);
    }
}
