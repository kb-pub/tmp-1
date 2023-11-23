package app.prodCons;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static app.prodCons.Util.log;
import static app.prodCons.Util.wrapExc;

public class Main {
    public static void main(String[] args) {
        int maxCount = 10000000;
        var storage = new Storage();
        var pool = Executors.newCachedThreadPool();
        var futures = new ArrayList<Future<?>>();
        try {
            for (int i = 0; i < 10; i++) {
                futures.add(pool.submit(new Producer(storage, maxCount)));
                futures.add(pool.submit(new Consumer(storage, maxCount)));
            }
            futures.forEach(f -> {
                var done = false;
                try {
                    while (!done) {
                        try {
                            f.get(2, TimeUnit.SECONDS);
                            done = true;
                        } catch (TimeoutException e) {
                            log("state: p=%d, c=%d, s=%d\n".formatted(
                                    storage.getItemsProducedCount(),
                                    storage.getItemsConsumedCount(),
                                    storage.getSize()));
                        }
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            });
            log("work done!");
            log("state: p=%d, c=%d, s=%d\n".formatted(
                    storage.getItemsProducedCount(),
                    storage.getItemsConsumedCount(),
                    storage.getSize()));
        }
        finally {
            futures.forEach(f -> wrapExc(() -> f.cancel(true)));
            pool.shutdown();
        }
    }
}
