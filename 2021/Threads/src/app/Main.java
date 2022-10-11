package app;

import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static app.Log.log;
import static java.util.stream.Collectors.toList;

public class Main {
    public static void main(String[] args) throws Exception {
        Thread.currentThread().setName("main");
        log("started");
        var threads = IntStream.range(0, 10)
                .mapToObj(i -> new Thread(Main::someWork, "thread-" + i))
                .peek(Thread::start)
                .collect(toList());

        threads.forEach(t -> {
            try {
                t.join(985);
                if (t.getState() != Thread.State.TERMINATED)
                    log(t.getName() + " is not terminated! [" + t.getState() + "]");
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        log("finished");
    }

    public static void someWork() {
        try {
            log("started work");
            Thread.sleep(3000);
            log("hello");
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            log("finished work");
        }
    }
}
