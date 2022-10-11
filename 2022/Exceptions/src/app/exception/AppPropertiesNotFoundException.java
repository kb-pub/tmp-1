package app.exception;

import app.AppException;

public class AppPropertiesNotFoundException extends AppException {
    public static final String MESSAGE = "app properties not found";

    public AppPropertiesNotFoundException() {
        super(MESSAGE);
    }

    public AppPropertiesNotFoundException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
