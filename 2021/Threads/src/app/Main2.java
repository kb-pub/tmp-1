package app;

import java.util.Arrays;
import java.util.concurrent.CancellationException;
import java.util.stream.IntStream;

import static app.Log.log;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

public class Main2 {
    public static final int THREAD_NUMBER = 10;

    public static void main(String[] args) throws Exception {
        var cancellationList = new boolean[THREAD_NUMBER];
        Thread.currentThread().setName("main");
        log("started");
        var threads = range(0, THREAD_NUMBER)
                .mapToObj(i -> new Thread(() -> someWork(i, cancellationList), "thread-" + i))
                .peek(Thread::start)
                .collect(toList());

        range(0, THREAD_NUMBER)
                .filter(i -> i % 3 == 0)
                .forEach(i -> cancellationList[i] = true);

        log(Arrays.toString(cancellationList));

        threads.forEach(t -> {
            try {
                t.join(985);
                if (t.getState() != Thread.State.TERMINATED)
                    log(t.getName() + " is not terminated! [" + t.getState() + "]");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        log("finished");
    }

    public static void someWork(int threadNumber, boolean[] cancellationList) {
        try {
            log("started work");
            for (int j = 0; j < 6; j++) {
                if (cancellationList[threadNumber])
                    throw new CancellationException();
                Thread.sleep(500);
            }
            log("hello");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (CancellationException e) {
            log("cancel work");
        } finally {
            log("finished work");
        }
    }
}
