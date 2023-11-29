package app;

import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("started main() in " + Thread.currentThread().getName());

        var works = IntStream.range(0, 3).mapToObj(i -> new Work()).toList();

        var threads = works.stream().map(w -> {
            var t = new Thread(w);
            t.start();
            return t;
        }).toList();

        Thread.sleep(3000);
        System.out.println("main() trying to finish threads");
        threads.forEach(Thread::interrupt);
        System.out.println("main() waiting threads");
        threads.forEach(t -> {
            try {
                System.out.println(t.getName() + " isAlive: " + t.isAlive());
                t.join();
                System.out.println(t.getName() + " isAlive: " + t.isAlive());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        works.forEach(w -> System.out.println(w.getFinishTime() + " finish time"));
        System.out.println("main() finished in " + Thread.currentThread().getName());
    }
}