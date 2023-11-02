package app;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

public class FixedStack<T extends Comparable<? super T>> {
    private final Object[] values;
    private int topIndex;

    public FixedStack(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be positive");
        }
        this.values = new Object[capacity];
        topIndex = 0;
    }

    public boolean nonEmpty() {
        return topIndex > 0;
    }

    public int size() {
        return topIndex;
    }

    public void push(T value) {
        if (topIndex >= values.length) {
            throw new StackOverflowException();
        }
        values[topIndex++] = value;
    }

    @SafeVarargs
    public final void pushAll(T... items) {
        for (var item : items)
            push(item);
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        if (topIndex == 0) {
            throw new StackUnderflowException();
        }
        return (T) values[--topIndex];
    }

    @SuppressWarnings("unchecked")
    public void flushFrom(FixedStack<? extends T> other) {
        while (other.nonEmpty())
            this.push(other.pop());
    }

    public void flushTo(FixedStack<? super T> other) {
        while (this.nonEmpty()) {
            other.push(this.pop());
        }
    }

    @SuppressWarnings("unchecked")
    public T getMax() {
        return (T) Arrays.stream(values)
                .limit(topIndex)
                .peek(x -> System.out.printf("elem '%s'\n", x))
                .map(Comparable.class::cast)
                .max(Comparator.comparing(x -> x))
                .orElse(null);
    }

    @Override
    public String toString() {
        return Arrays.stream(values)
                .limit(topIndex)
                .map(Object::toString)
                .collect(Collectors.joining(", ", "{", "}"));
    }
}





















