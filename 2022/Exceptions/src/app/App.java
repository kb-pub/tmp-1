package app;

import app.exception.AppPropertiesNotFoundException;
import app.exception.DetailedTestAppException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class App {
    public static void main(String[] args) throws FileNotFoundException {
        try {
            new A().method1(true);
        } catch (AppException t) {
            t.printStackTrace();
        } catch (RuntimeException e) {
            System.out.println("caught in main: " + e.getMessage());
        }

        System.out.println("operation 4");
    }
}

class A {
    public void method1(boolean doException)/* throws Exception*/ {
        try {
            new B().method2(doException);
            System.out.println("operation 3");
        } finally {
            System.out.println("finally in method 1");
        }
    }
}

class B {
    public void method2(boolean doException)/* throws DetailedTestAppException*/ {
        try {
            try {
                System.out.println("operation 1");
//            (new int[5])[10] = 50;
                try {
                    var properties = Files.readString(Path.of("/home/kb/asdasdasd"));
                } catch (IOException e) {
                    throw new AppPropertiesNotFoundException(e);
                }

                if (doException)
                    throw new DetailedTestAppException("operation error!");
                System.out.println("operation 2_1");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("caught in method 2: " + e.getMessage());
            } finally {
                System.out.println("finally inner in method 2");
            }
//        } catch (RuntimeException e) {
//            System.out.println("caught outer in method 2: " + e.getMessage());
        } finally {
            System.out.println("finally outer in method 2");
        }

        System.out.println("operation 2_2");
    }
}