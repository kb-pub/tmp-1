package app;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CancellationException;

import static app.Log.log;
import static java.lang.Thread.sleep;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

public class Main3 {
    public static final int THREAD_NUMBER = 10;

    public static void main(String[] args) throws Exception {
        Thread.currentThread().setName("main");

        log("started");

        var threads = createThreads();

        sleep(1500);

        cancelSomeThreads(threads);

        joinThreads(threads);

        log("finished");
    }

    public static void someWork() {
        try {
            log("started work");

            sleep(3000);

            log("hello");
        } catch (InterruptedException e) {
            log("interrupted work");
        } finally {
            log("finished work");
        }
    }

    private static List<Thread> createThreads() {
        return range(0, THREAD_NUMBER)
                .mapToObj(i -> new Thread(Main3::someWork, "thread-" + i))
                .peek(Thread::start)
                .collect(toList());
    }

    private static void cancelSomeThreads(List<Thread> threads) {
        range(0, THREAD_NUMBER)
                .filter(i -> i % 3 == 0)
                .forEach(i -> threads.get(i).interrupt());
    }

    private static void joinThreads(List<Thread> threads) {
        threads.forEach(t -> {
            try {
                t.join(985);
                if (t.getState() != Thread.State.TERMINATED)
                    log(t.getName() + " is not terminated! [" + t.getState() + "]");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
