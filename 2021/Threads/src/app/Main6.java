package app;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.concurrent.*;
import java.util.function.Predicate;

import static app.Log.log;
import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

public class Main6 {
    public static final int THREAD_NUMBER = 10;
    //    public static final ExecutorService pool = Executors.newCachedThreadPool();
    public static final ExecutorService pool = Executors.newFixedThreadPool(3);

    public static void main(String[] args) throws Exception {
        try {
            var futures = range(0, THREAD_NUMBER)
                    .mapToObj(Main6::someAsyncWork)
                    .collect(toList());

            range(0, THREAD_NUMBER)
                    .filter(i -> i % 3 == 0)
                    .mapToObj(futures::get)
                    .forEach(f -> f.cancel(true));

            futures.stream()
                    .filter(not(Future::isCancelled))
                    .forEach(f -> {
                        try {
                            log(f.get());
                        } catch (Exception e) {
                            log("ERROR: [" + e.getClass().getName() + "] " + e.getMessage());
                        }
                    });
        } finally {
            pool.shutdown();
        }
    }

    public static Future<String> someAsyncWork(int num) {
        return pool.submit(() -> {
            log("started");
            sleep(3000);
            log("finished");
            return currentThread().getName() + " result";
        });
    }
}
