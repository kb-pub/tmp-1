package app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws Throwable {
        System.out.println("started");
        try {
            System.out.println("in main() before calling");
            new A().methodA(100);
            System.out.println("in main() after calling");
        } catch (TestException e) {
            System.out.println("in main() TestException caught: " + e);
        } catch (Exception e) {
            System.out.println("in main() Exception caught: " + e);
        } finally {
            System.out.println("in main() finally");
        }
        System.out.println("finished!");

        printFile(Paths.get("/home/kb/test.txt"));
    }

    private static void printFile(Path file) {
        try (Stream<String> lines = Files.lines(file)) {
            lines.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        Stream<String> lines = null;
//        try {
//            (lines = Files.lines(file))
//                    .forEach(line -> System.out.println());
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (lines != null) {
//                try {
//                    lines.close();
//                }
//                catch (Throwable t) {
//                    /* do nothing */
//                    //t.printStackTrace();
//                }
//            }
//        }
    }
}

class A {
    public void methodA(int x) throws Exception {
        System.out.println("in methodA() before calling");
        try {
            new B().methodB(x);
        } catch (Throwable t) {
            System.out.println("in methodA Throwable caught: " + t);
            throw new RuntimeException(t);
        } finally {
            System.out.println("in methodA finally");
        }
        System.out.println("in methodA() after calling");
    }
}

class B {
    public void methodB(int x) throws Throwable {
        System.out.println("in methodB() before if");
        try {
            if (x < 0) {
                System.out.println("in methodB() raised exception!");
                throw new Throwable("init exception");
                // ...
            }
        } finally {
            System.out.println("in methodB() finally");
        }
        System.out.println("in methodB() after if");
    }
}





























