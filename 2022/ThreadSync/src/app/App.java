package app;

import java.util.Arrays;
import java.util.stream.IntStream;

public class App {
    public static void main(String[] args) {
        var accounts = IntStream.range(0, 10)
                .mapToObj(i -> new Account())
                .toArray(Account[]::new);

        var workers = IntStream.range(0, 10)
                .mapToObj(i -> new Worker(accounts))
                .toList();

        workers.forEach(Thread::start);

        workers.forEach(w -> wrapExc(w::join));

        System.out.println(Arrays.stream(accounts)
                .mapToLong(Account::getAmount)
                .sum());
        System.out.println(Arrays.stream(accounts)
                .map(Account::getAmount)
                .toList());
    }

    interface ThrowableRunnable {
        void run() throws Exception;
    }

    private static void wrapExc(ThrowableRunnable r) {
        try {
            r.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
