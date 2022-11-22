package app;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Worker extends Thread {
    private final static Lock LOCK = new ReentrantLock();

    private static final Random RANDOM = new Random();

    private final Account[] accounts;

    public Worker(Account[] accounts) {
        this.accounts = accounts;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++)
            performTransfer();
        System.out.println(this + " finished");
    }

    private void performTransfer() {
        var done = false;
        while (!done) {
            Account accFrom, accTo;
            do {
                accFrom = getRandomAccount();
                accTo = getRandomAccount();
            }
            while (accFrom == accTo);

            var transfer = RANDOM.nextInt(10000);
//            if (accFrom.getAmount() >= transfer) {
            try {
                accFrom.subAmount(transfer);
                accTo.addAmount(transfer);
                done = true;
            }
            catch (NotEnoughMoneyException e) {
                /* do nothing */
            }
//            }
        }
    }

    private Account getRandomAccount() {
        return accounts[RANDOM.nextInt(accounts.length)];
    }
}
