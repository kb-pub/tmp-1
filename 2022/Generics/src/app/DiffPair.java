package app;

public class DiffPair<T, V> {
    private T first;
    private V second;

    public DiffPair(T first, V second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public V getSecond() {
        return second;
    }

    public void setSecond(V second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return "DiffPair{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}
