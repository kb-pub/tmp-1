package app;

import java.time.LocalTime;

public class Work implements Runnable {
    private LocalTime finishTime;

    public LocalTime getFinishTime() {
        return finishTime;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.printf("[%s] hello from %s\n", LocalTime.now(), Thread.currentThread().getName());
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            System.out.println("InterruptedException caught in " + Thread.currentThread().getName());
        } finally {
            System.out.println(Thread.currentThread().getName() + " finished");
            finishTime = LocalTime.now();
        }
    }
}
