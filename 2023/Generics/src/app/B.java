package app;

public class B extends A {
    private static int idSeq = 0;
    private final int id = idSeq++;

    @Override
    public String toString() {
        return getClass().getSimpleName() + id;
    }

    @Override
    public int compareTo(A o) {
        return super.compareTo(o);
    }
}
