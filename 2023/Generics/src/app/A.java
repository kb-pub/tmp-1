package app;

public class A implements Comparable<A> {
    private static int idSeq = 0;
    private final int id = idSeq++;

    @Override
    public String toString() {
        return getClass().getSimpleName() + id;
    }

    @Override
    public int compareTo(A o) {
        return o.id - id;
    }
}
