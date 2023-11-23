package app.prodCons;

import java.util.concurrent.atomic.AtomicInteger;

public class StorageSync {
    public static final int CAPACITY = 100;

    private final Item[] items = new Item[CAPACITY];
    private int pos = 0;

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

    public synchronized int getItemsProducedCount() {
        return itemsProducedCount;
    }

    public synchronized int getItemsConsumedCount() {
        return itemsConsumedCount;
    }

    public synchronized int getSize() {
        return pos;
    }

    public synchronized boolean isFull() {
        return pos == CAPACITY;
    }

    public synchronized boolean isEmpty() {
        return pos == 0;
    }

    public synchronized void put(Item item) {
//        if (pos == CAPACITY) {
//            throw new StorageIsFullException();
//        }
        try {
            while (isFull()) {
                wait();
            }
            itemsProducedCount++;
            notifyAll();
            items[pos++] = item;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized Item get() {
//        if (pos == 0) {
//            throw new StorageIsEmptyException();
//        }
        try {
            while (isEmpty()) {
                wait();
            }
            itemsConsumedCount++;
            notifyAll();
            return items[--pos];
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
