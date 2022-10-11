package app;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import static app.Log.log;
import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

public class Main5 {
    public static final int THREAD_NUMBER = 10;

    public static void main(String[] args) throws Exception {
        var futures = range(0, THREAD_NUMBER)
                .mapToObj(Main5::someAsyncWork)
                .collect(toList());

        range(0, THREAD_NUMBER)
                .filter(i -> i % 3 == 0)
                .mapToObj(futures::get)
                .forEach(f -> f.cancel(true));

        futures.forEach(f -> {
            try {
                if ( ! f.isCancelled())
                    log(f.get());
            } catch (Exception e) {
                log("ERROR: [" + e.getClass().getName() + "] " + e.getMessage());
            }
        });
    }

    public static Future<String> someAsyncWork(int num) {
        var future = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                log("started");
                sleep(3000);
                log("finished");
                return currentThread().getName() + " result";
            }
        });
        new Thread(future).start();
        return future;
    }
}
