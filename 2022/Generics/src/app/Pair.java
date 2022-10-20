package app;

import java.io.FileNotFoundException;
import java.io.Serializable;

public class Pair<T extends Serializable> extends DiffPair<T, T> {
//    static T r;

    public Pair(T first, T second) {
        super(first, second);
//        var x = new T();
//        var y = new T[10];
        T t1;
        T[] t2;
    }

    public boolean equals(T otherPair) {
        return false;
    }

    private void testCatch() {
        try {
            this.<FileNotFoundException>testExc(new FileNotFoundException());
        }
        catch (FileNotFoundException e) {

        }
    }

    private <E extends Exception> void testExc(E exc) throws E {
        throw exc;
    }
}
