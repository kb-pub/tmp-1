package app;

import java.io.Serializable;

public class MinMaxPair<T extends Comparable<T> & Serializable> extends Pair<T> {
    public MinMaxPair(T first, T second) {
        super(first.compareTo(second) < 0 ? first : second,
                second.compareTo(first) > 0 ? second : first);
    }
}
