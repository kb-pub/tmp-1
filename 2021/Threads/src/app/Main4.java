package app;

import java.util.ArrayList;
import java.util.List;

import static app.Log.log;
import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

public class Main4 {
    public static final int THREAD_NUMBER = 10;

    public static void main(String[] args) throws Exception {
        var results = new ArrayList<WorkResult>(10);
        for (int i = 0; i < THREAD_NUMBER; i++) {
            results.add(null);
        }

        Thread.currentThread().setName("main");

        log("started");

        var threads = createThreads(results);

        sleep(1500);

        cancelSomeThreads(threads);

        joinThreads(threads);

        log("RESULTS:");
        results.stream()
                .filter(not(WorkResult::isInterrupted))
                .map(WorkResult::getResult)
                .forEach(Log::log);

        log("finished");
    }

    public static void someWork(int num, List<WorkResult> results) {
        try {
            log("started work");

            sleep(3000);

            results.set(num, new WorkResult("hello from " + currentThread().getName()));
        } catch (InterruptedException e) {
            log("interrupted work");
            results.set(num, WorkResult.CANCELLED);
        } finally {
            log("finished work");
        }
    }

    private static List<Thread> createThreads(List<WorkResult> results) {
        return range(0, THREAD_NUMBER)
                .mapToObj(i -> new Thread(() -> someWork(i, results), "thread-" + i))
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

    private static class WorkResult {
        public static final WorkResult CANCELLED = new WorkResult();

        private final boolean interrupted;
        private final String result;

        public WorkResult(String result) {
            interrupted = false;
            this.result = result;
        }

        private WorkResult() {
            interrupted = true;
            result = null;
        }

        public boolean isInterrupted() {
            return interrupted;
        }

        public String getResult() {
            return result;
        }

        @Override
        public String toString() {
            return "WorkResult{" +
                    "interrupted=" + interrupted +
                    ", result='" + result + '\'' +
                    '}';
        }
    }
}
