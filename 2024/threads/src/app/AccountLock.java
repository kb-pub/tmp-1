package app;

import java.util.concurrent.locks.ReentrantLock;

public class AccountLock implements Account {
    private final ReentrantLock lock = new ReentrantLock();

    private static int idCount = 0;
    private int id = idCount++;
    private volatile int amount;

    public AccountLock(int amount) {
        this.amount = amount;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public void withdraw(int value) {
        lock.lock();
        try {
            amount -= value;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void replenish(int value) {
        lock.lock();
        try {
            amount += value;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        return "Account-%d(%d)".formatted(id, amount);
    }
}
