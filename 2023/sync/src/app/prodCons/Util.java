package app.prodCons;

public class Util {
    public static void wrapExc(ThrowableRunnable runnable) {
        try {
            runnable.run();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static interface ThrowableRunnable {
        void run() throws Exception;
    }

    public static void log(Object o) {
        System.out.println(o);
    }
}
