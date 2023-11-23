package app.acc;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class Main {
    public static final Lock LOCK = new ReentrantLock();

    public static void main(String[] args) {
        var accounts = new Account[10];
        IntStream.range(0, accounts.length).forEach(i -> accounts[i] = new Account());
        var random = new Random();

        var threads = IntStream.range(0, 10)
                .mapToObj(i -> new Thread(new TransactionWorker(accounts, random)))
                .toList();

        var start = System.nanoTime();

        threads.forEach(Thread::start);

        threads.forEach(t -> {
            try {
                t.join();
            }
            catch (Throwable th) {
                th.printStackTrace();
            }
        });

        System.out.println((System.nanoTime() - start) / 1000000.0);

        System.out.println(
                Arrays.stream(accounts)
                        .mapToInt(Account::getValue)
                        .sum());
        System.out.println(Arrays.toString(accounts));
    }
}