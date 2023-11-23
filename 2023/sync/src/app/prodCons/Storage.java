package app.prodCons;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Storage {
    public static final int CAPACITY = 100;

    private final Item[] items = new Item[CAPACITY];
    private int pos = 0;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condFull = lock.newCondition();
    private final Condition condEmpty = lock.newCondition();

    private final AtomicInteger itemsProduced = new AtomicInteger();
    private final AtomicInteger itemsConsumed = new AtomicInteger();

    public AtomicInteger getItemsProduced() {
        return itemsProduced;
    }

    public AtomicInteger getItemsConsumed() {
        return itemsConsumed;
    }

    private int itemsProducedCount = 0;
    private int itemsConsumedCount = 0;

    public int getItemsProducedCount() {
        lock.lock();
        try {
            return itemsProducedCount;
        }
        finally {
            lock.unlock();
        }
    }

    public int getItemsConsumedCount() {
        lock.lock();
        try {
            return itemsConsumedCount;
        }
        finally {
            lock.unlock();
        }
    }

    public int getSize() {
        lock.lock();
        try {
            return pos;
        }
        finally {
            lock.unlock();
        }
    }

    public boolean isFull() {
        lock.lock();
        try {
            return pos == CAPACITY;
        }
        finally {
            lock.unlock();
        }
    }

    public boolean isEmpty() {
        lock.lock();
        try {
            return pos == 0;
        }
        finally {
            lock.unlock();
        }
    }

    public void put(Item item) {
        lock.lock();
        try {
            while (isFull()) {
                condFull.await();
            }
            itemsProducedCount++;
            condEmpty.signalAll();
            items[pos++] = item;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
    }

    public Item get() {
        lock.lock();
        try {
            while (isEmpty()) {
                condEmpty.await();
            }
            itemsConsumedCount++;
            condFull.signalAll();
            return items[--pos];
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        finally {
            lock.unlock();
        }
    }
}
