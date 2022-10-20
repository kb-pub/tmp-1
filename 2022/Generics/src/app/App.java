package app;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class App {
    public static void main(String[] args) {
        var x = getX();
        var y = 0.6;
        System.out.println("Result = " + (x + y));
    }

    private static double getX() {
        return 0.3;
    }

    private static void test3 () {
//        var list = new List<String>[10];
    }

    private static void test2() {
        Collection<String> coll1 = null;
        List<String> list1 = new ArrayList<>();
        //list1 = coll1;
        coll1 = list1;

        Number n = null;
        Integer i = null;
        //i = n;
        n = i;

        List<Integer> listI = null;
        List<Number> listN = null;
        //listI = listN;
        //listN = listI;

        List<? extends Number> listExtN = null;
        listExtN = listI;
        listExtN.get(0).shortValue();
        //listExtN.add(5);

        List<? super Integer> listSupI = null;
        listSupI = listI;
        listSupI = listN;
        var x = listSupI.get(0);
        listSupI.add(4);
    }

    private static void test1() {
        var pair = new DiffPair<Number, String>(4, "qwerty");
        pair.setFirst(123456789.0);
        System.out.println(pair.getFirst().shortValue());

        var pair2 = new Pair<String>("4", "qwerty");
        pair2.setFirst("123456789.0");
        System.out.println(pair2.getFirst().toUpperCase());

        var pair3 = new StringPair("4", "qwerty");
        pair3.setFirst("123456789.0");
        System.out.println(pair3.getFirst().toUpperCase());
    }
}
