package ext;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class App {
    public static void main(String[] args) throws Exception {
        var pool = new ThreadPoolExecutor(
                10,
                20,
                120,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(2048),
                new ThreadPoolExecutor.AbortPolicy());
        var map = new ConcurrentHashMap<String, Integer>();
        var key = "KEY";
        map.put(key, 0);
        var individualCounts = new long[10];
        IntStream.range(0, individualCounts.length)
                .mapToObj(x -> new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 1000000; i++) {
//                            var value = map.get(key);
//                            map.put(key, value + 1);
                            map.compute(key, (k, v) -> v + 1);
                        }
                    }
                })
                .forEach(pool::submit);
        Thread.sleep(1000);
        pool.shutdown();
        pool.awaitTermination(1000, TimeUnit.SECONDS);
        System.out.println(map);
    }
}
