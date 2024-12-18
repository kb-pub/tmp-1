package app;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AccountNoSync implements Account {
    public final ReentrantLock lock = new ReentrantLock();

    private static int idCount = 0;
    private int id = idCount++;
    private volatile int amount;

    public AccountNoSync(int amount) {
        this.amount = amount;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public void withdraw(int value) {
        amount -= value;
    }

    @Override
    public void replenish(int value) {
        amount += value;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Lock getLock() {
        return lock;
    }

    @Override
    public String toString() {
        return "Account-%d(%d)".formatted(id, amount);
    }
}
