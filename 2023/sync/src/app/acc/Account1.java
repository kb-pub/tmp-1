package app.acc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account1 {
    private volatile int value = 1000;
    private final Lock lock = new ReentrantLock();

    public int getValue() {
        return value;
    }

    public void inc(int value) {
        lock.lock();
//        Main.LOCK.lock();
        try {
            this.value += value;
        }
        finally {
            lock.unlock();
//            Main.LOCK.unlock();
        }
    }

    private final Lock decLock = new ReentrantLock();
    public void dec(int value) {
        lock.lock();
//        Main.LOCK.lock();
        try {
            this.value -= value;
        }
        finally {
            lock.unlock();
//            Main.LOCK.unlock();
        }
    }
}
