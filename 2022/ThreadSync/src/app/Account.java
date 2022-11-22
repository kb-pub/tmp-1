package app;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
    private final Lock lock = new ReentrantLock();
    private final Condition notEnoughAmountCondition = lock.newCondition();

    private volatile long amount = 100000;

    public long getAmount() {
        return amount;
    }

    public void addAmount(int amount) {
        lock.lock();
        this.amount += amount;
        notEnoughAmountCondition.signalAll();
        lock.unlock();
    }

    public void subAmount(int amount) {
        try {
            lock.lock();
            if (this.amount < amount)
                throw new NotEnoughMoneyException();
//            while (this.amount < amount)
//                notEnoughAmountCondition.await();
            this.amount -= amount;
            if (this.amount < 0)
                System.out.println(this);
        }
//        catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        return "Account{" +
                "amount=" + amount +
                '}';
    }
}
