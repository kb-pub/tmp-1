package app.acc;

import java.util.Random;

public class TransactionWorker implements Runnable {
    private final Account[] accounts;
    private final Random random;

    public TransactionWorker(Account[] accounts, Random random) {
        this.accounts = accounts;
        this.random = random;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000000; i++) {
            var ind1 = random.nextInt(accounts.length);
            var ind2 = random.nextInt(accounts.length);
            var value = random.nextInt(100);
            accounts[ind1].dec(value);
            accounts[ind2].inc(value);
        }
    }
}
