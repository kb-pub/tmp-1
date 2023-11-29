package app;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Pool {
    public static void main(String[] args) {
        class SimpleWork implements Runnable {
            private int count;

            public int getCount() {
                return count;
            }

            @Override
            public void run() {
                count++;
            }
        }
        var work = new SimpleWork();

        long start = System.nanoTime();
        IntStream.range(0, 100000).forEach(i -> work.run());
        double noThreadTime = System.nanoTime() - start;

        start = System.nanoTime();
        IntStream.range(0, 100000).forEach(i -> new Thread(work).start());
        double allThreadTime = System.nanoTime() - start;

        var pool = new ThreadPoolExecutor(16, 16,
                1, TimeUnit.MINUTES, new ArrayBlockingQueue<>(1024));
        start = System.nanoTime();
        try {
            IntStream.range(0, 100000).forEach(i -> pool.submit(work));
            while (pool.getCompletedTaskCount() != 100000) {
                Thread.yield();
            }
        } finally {
            pool.shutdown();
        }
        double poolThreadTime = System.nanoTime() - start;

        System.out.println(work.getCount());
        System.out.println("noThreadTime: " + noThreadTime / 1000000);
        System.out.println("allThreadTime: " + allThreadTime / 1000000);
        System.out.println("poolThreadTime: " + poolThreadTime / 1000000);
    }
}
