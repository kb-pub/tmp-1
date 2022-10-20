package app;

import java.io.Serializable;

public class Pairs {
    public static <T extends Serializable> void swap(Pair<T> pair) {
        var tmp = pair.getFirst();
        pair.setFirst(pair.getSecond());
        pair.setSecond(tmp);
    }

//    public static void swap(Pair pair) {
//        var tmp = pair.getFirst();
//        pair.setFirst(pair.getSecond());
//        pair.setSecond("tmp");
//    }

    public static void swapQM(Pair<?> pair) {
        swap(pair);
    }
}
