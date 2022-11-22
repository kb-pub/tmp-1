package app;

public class Account2 {
    private volatile long amount = 100000;

    public long getAmount() {
        return amount;
    }

    public synchronized void addAmount(int amount) {
        this.amount += amount;
    }

    public synchronized void subAmount(int amount) {
        if (this.amount < amount)
            throw new NotEnoughMoneyException();
        this.amount -= amount;
        if (this.amount < 0)
            System.out.println(this);
    }

    @Override
    public String toString() {
        return "Account{" +
                "amount=" + amount +
                '}';
    }
}
