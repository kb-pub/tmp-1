package app2;

public class Util {
    public static void wrap(ThrowableRunnable r) {
        try {
            r.run();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public interface ThrowableRunnable {
        void run() throws Exception;
    }
}
