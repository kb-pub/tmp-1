package app;

import java.io.IOException;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) throws ZeroDivisorException {
        try {
            var result = doOperation();
            System.out.println("result = " + result);
        } catch (OperationNotImplementedYetException e) {
            System.out.println("not implemented: " + e.getMessage());
        } catch (IllegalArgumentException | AppException e) {
            e.printStackTrace();
        }
//        catch (ZeroDivisorException e) {
//            System.out.println("divisor is 0!");
//        }
        catch (Exception e) {
            System.err.println("UNEXPECTED EXCEPTION");
            e.printStackTrace();
        }
        System.out.println("goodbye");
    }

    static void recur() {
        recur();
    }

    static double doOperation()
            throws ZeroDivisorException, OperationNotImplementedYetException {
        try {
            return div(5, 2);
        } catch (ZeroDivisorException e) {
            System.out.println("Error: " + e.getMessage());
            return 0;
        } finally {
            System.out.println("finally in div");
        }
    }

    static double div(double x, double y)
            throws ZeroDivisorException, OperationNotImplementedYetException {
        var calc = new Calc();
        return calc.handle(x, y, Calc.Operation.DIVISION);
    }

    static double sum(double x, double y)
            throws ZeroDivisorException, OperationNotImplementedYetException {
        var calc = new Calc();
        return calc.handle(x, y, Calc.Operation.ADDITION);
    }
}

class Calc {
    public enum Operation {ADDITION, SUBTRACTION, DIVISION}

    public double handle(double x, double y, Operation op)
            throws ZeroDivisorException, OperationNotImplementedYetException {
        try {
            return switch (op) {
                case DIVISION -> division(x, y);
                default -> throw new OperationNotImplementedYetException(op.toString());
            };
        } finally {
            System.out.println("finally in handle");
        }
    }

    private double division(double x, double y) throws ZeroDivisorException {
        try (var writer = new PrintWriter("/home/kb/text/TESTFILE.txt")) {
            writer.println("division log: " + x + " / " + y);
            if (y == 0)
                throw new ZeroDivisorException("y == 0!");
            return x / y;
        } catch (IOException e) {
            throw new ZeroDivisorException(e);
        }
    }
}

class AppException extends Exception {
    public AppException(String message) {
        super(message);
    }

    public AppException(Throwable cause) {
        super(cause);
    }
}

class ZeroDivisorException extends AppException {
    public ZeroDivisorException(String message) {
        super(message);
    }

    public ZeroDivisorException(Throwable t) {
        super(t);
    }
}

class OperationNotImplementedYetException extends AppException {
    public OperationNotImplementedYetException(String message) {
        super(message);
    }
}