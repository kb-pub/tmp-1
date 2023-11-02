package app;

public class StackTest {
    public static void main(String[] args) {
        test2();
    }

    private static void test2() {
        var stackA = new FixedStack<A>(10);
        stackA.pushAll(new A(), new A(), new A());

        var stackB = new FixedStack<B>(10);
        stackB.pushAll(new B(), new B(), new B());

        System.out.println(stackA);
        System.out.println(stackB);

        System.out.println(stackA.getMax());
    }

    private static void test1() {
        int size = 10;
        var stack = new FixedStack<Integer>(size);
        for (int i = 0; i < size; i++) {
            stack.push(i);
        }

        System.out.println("FULL: " + stack);

        for (int i = 0; stack.nonEmpty(); i++) {
            Integer value = stack.pop();
            System.out.printf("%d -> %s\n", i, "%s [%d]".formatted(value, value.byteValue()));
        }

        System.out.println("EMPTY: " + stack);
    }
}
