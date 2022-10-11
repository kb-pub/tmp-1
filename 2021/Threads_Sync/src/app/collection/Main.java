package app.collection;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) throws Exception {
        var map = new ConcurrentHashMap<String, Long>(); // Java 8

        var accounts = new Accounts(10, 10000);
        var threads = IntStream.range(0, 100)
                .mapToObj(i -> new Thread(new Worker(accounts)))
                .peek(Thread::start)
                .collect(Collectors.toList());

        Thread.sleep(3000);

        threads.forEach(Thread::interrupt);

        threads.forEach(Main::join);

        System.out.println(accounts.getAccounts());
        System.out.println("Total: " + accounts.getAccounts().stream()
                .mapToInt(AtomicInteger::get).sum());
    }

    private static void join(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class Accounts {
    private final static Random random = new Random();
    private final List<AtomicInteger> accounts;

    public Accounts(int accountNum, int initAmount) {
        this.accounts = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < accountNum; i++) {
            accounts.add(new AtomicInteger(initAmount));
        }
    }

    public List<AtomicInteger> getAccounts() {
        return accounts;
    }

    public int randomAccount() {
        return random.nextInt(accounts.size());
    }

    public /*synchronized*/ boolean transfer(int accountFrom, int accountTo, int transferAmount) {
        boolean notInterrupted;

//        try {
            while ((notInterrupted = !Thread.interrupted()) &&
                    accounts.get(accountFrom).get() < transferAmount)
                Thread.yield(); //wait();
//        } catch (InterruptedException e) {
//            notInterrupted = false;
//        }

        if (notInterrupted) {
            accounts.get(accountFrom).addAndGet( - transferAmount);
            accounts.get(accountTo).addAndGet(transferAmount);
        }

//        notifyAll();

        return notInterrupted;
    }
}

class Worker implements Runnable {
    private final static Random random = new Random();

    private int cnt = 0;
    private final Accounts accounts;

    public Worker(Accounts accounts) {
        this.accounts = accounts;
    }

    @Override
    public void run() {
        boolean notInterrupted = true;
        while (notInterrupted && !Thread.interrupted()) {
            notInterrupted = accounts.transfer(accounts.randomAccount(),
                    accounts.randomAccount(),
                    random.nextInt(1000));

            someWork();
        }
        System.out.println(Thread.currentThread().getName() + " cnt: " + cnt);
    }


    private void someWork() {
        for (int i = 0; i < 100; i++) {
            cnt++;
        }
    }
}