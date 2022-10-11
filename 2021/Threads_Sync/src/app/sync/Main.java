package app.sync;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) throws Exception {
        var accounts = new Accounts(10, 10000);

        var threads = IntStream.range(0, 100)
                .mapToObj(i -> new Thread(new Worker(accounts)))
                .peek(Thread::start)
                .collect(Collectors.toList());

        Thread.sleep(3000);

        threads.forEach(Thread::interrupt);

        threads.forEach(Main::join);

        System.out.println(Arrays.toString(accounts.getAccounts()));
        System.out.println("Total: " + Arrays.stream(accounts.getAccounts()).sum());
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
    private final int[] accounts;

    public Accounts(int accountNum, int initAmount) {
        Object x;
        this.accounts = new int[accountNum];
        Arrays.fill(accounts, initAmount);
    }

    public int[] getAccounts() {
        return accounts;
    }

    public int randomAccount() {
        return random.nextInt(accounts.length);
    }

    public synchronized boolean transfer(int accountFrom, int accountTo, int transferAmount) {
        boolean notInterrupted;

        try {
            while ((notInterrupted = !Thread.interrupted()) && accounts[accountFrom] < transferAmount)
                wait();
        } catch (InterruptedException e) {
            notInterrupted = false;
        }

        if (notInterrupted) {
            accounts[accountFrom] -= transferAmount;
            accounts[accountTo] += transferAmount;
        }

        notifyAll();

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