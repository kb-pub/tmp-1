package app;

import java.util.List;
import java.util.Random;

public class Worker implements Runnable {
    private static int idCount = 0;
    private int id = idCount++;

    private final Random random = new Random();
    private final int iterations;
    private final List<? extends Account> accounts;

    public Worker(int iterations, List<? extends Account> accounts) {
        this.iterations = iterations;
        this.accounts = accounts;
    }

    @Override
    public void run() {
        for (int i = 0; i < iterations; i++) {
            var from = accounts.get(random.nextInt(accounts.size()));
            var to = accounts.get(random.nextInt(accounts.size()));
            var amount = random.nextInt(1000);

            var first = from.getId() < to.getId() ? from : to;
            var second = from.getId() >= to.getId() ? from : to;

            first.getLock().lock();
            second.getLock().lock();
            try {
                if (from.getAmount() < amount) {
                    continue;
                }
                from.withdraw(amount);
                to.replenish(amount);
                System.out.printf("transferred %d from %s to %s\n", amount, from, to);
            } finally {
                from.getLock().unlock();
                to.getLock().unlock();
            }
        }
    }

    @Override
    public String toString() {
        return "Worker-%d".formatted(id);
    }

}
