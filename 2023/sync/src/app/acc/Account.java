package app.acc;

public class Account {
    private /*volatile*/ int value = 1000;
//    private final Object object = new Object();

    public synchronized int getValue() {
        return value;
    }

    public synchronized void inc(int amount) {
        value += amount;
    }

    public synchronized void dec(int amount) {
        value -= amount;
    }

    @Override
    public String toString() {
        return value + "";
    }
}
