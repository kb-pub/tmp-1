package app;

public class App {
    public static void main(String[] args) {
        var interval = new Interval<B>();
        setB(interval);
        printSecond(interval);
    }

    private static void setB(Interval<? super B> interval) {
        interval.setFirst(new B());
        interval.setSecond(new B());
    }

    private static void printSecond(Interval<? extends A> interval) {
        A a = interval.getSecond();
        System.out.println(a);
    }

    private static void printClassName(Class<?> type) {
        System.out.println(type.getCanonicalName());
    }

    private static void printFirstAndSecond(Interval<?> interval) {
        printFirstAndSecondTyped(interval);
    }

    private static <T> void printFirstAndSecondTyped(Interval<T> interval) {
        printFirstAndSecondSeparated(interval.getFirst(), interval.getSecond());
    }

    private static <T> void printFirstAndSecondSeparated(T first, T second) {

    }

    static class A {}

    static class B extends A {}

    static class C extends B {}

    public static class Interval<T> {
        private T first, second;

        public T getFirst() {
            return first;
        }

        public void setFirst(T first) {
            this.first = first;
        }

        public T getSecond() {
            return second;
        }

        public void setSecond(T second) {
            this.second = second;
        }
    }

//    private <T> Interval<T>[] createIntervalArray() {
//        return new Interval<T>[100];
//    }

    private <T> T[] createVarargsArray(T ... args) {
        return args;
    }

//    private <T> T[] createArray() {
//        return new T[100];
//    }
}
