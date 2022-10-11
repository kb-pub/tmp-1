package app;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {
    public static void log(Object msg) {
        System.out.println(Thread.currentThread().getName() + " {"
                + LocalDateTime.now().format(DateTimeFormatter.ISO_TIME)
                + "}: " + msg);
    }
}
