package app;

import java.util.concurrent.locks.Lock;

public interface Account {
    int getAmount();

    void withdraw(int value);

    void replenish(int value);

    @Override
    String toString();

    default Lock getLock() {
        throw new UnsupportedOperationException();
    }

    int getId();
}
