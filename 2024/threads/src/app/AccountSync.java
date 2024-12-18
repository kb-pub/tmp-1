package app;

public class AccountSync implements Account {
    private static int idCount = 0;
    private int id = idCount++;
    private volatile int amount;

    public AccountSync(int amount) {
        this.amount = amount;
    }

    @Override
    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public synchronized void withdraw(int value) {
            amount -= value;
    }

    public synchronized void replenish(int value) {
            amount += value;
    }

    @Override
    public String toString() {
        return "Account-%d(%d)".formatted(id, amount);
    }
}
