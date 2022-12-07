package util;

import exception.AppException;

import java.util.concurrent.Callable;

public class Util {
    public static <T> T rethrow(Callable<T> action) {
        try {
            return action.call();
        } catch (Exception e) {
            throw new AppException(e.getMessage(), e);
        }
    }

    public static void rethrow(ThrowableRunnable action) {
        try {
            action.run();
        } catch (Exception e) {
            throw new AppException(e.getMessage(), e);
        }
    }

    public interface ThrowableRunnable {
        void run() throws Exception;
    }
}
