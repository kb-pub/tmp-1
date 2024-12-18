package app;

import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) throws Exception {
        List<? extends Account> accounts = IntStream.range(0, 10)
                .mapToObj(x -> new AccountNoSync(10000))
                .toList();
        var initialSum = accounts.stream().mapToInt(Account::getAmount).sum();
        var workers = IntStream.range(0, 20)
                .mapToObj(x -> new Worker(10000, accounts))
                .toList();
        var threads = workers.stream().map(Thread::new).toList();
        var start = System.nanoTime();
        threads.forEach(Thread::start);
        System.out.println("threads started!");
        joinAll(threads);
        System.out.println("all done!");
        System.out.println("time elapsed = " + (System.nanoTime() - start) / 1000000 + " ms");
        System.out.println(accounts);
        System.out.println("current sum = " +
                accounts.stream().mapToInt(Account::getAmount).sum());
        System.out.println("init sum = " + initialSum);
    }

    private static void joinAll(Collection<? extends Thread> threads) {
        try {
            for (var thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}