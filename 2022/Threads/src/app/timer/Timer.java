package app.timer;

import app.Logger;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

public class Timer {
    private final int id;
    private final int seconds;

    private final Logger l;
    private LocalDateTime startTime;
    private final Consumer<Integer> shutdownAction;
    private Thread thread;

    public Timer(int id, int seconds, Logger logger, Consumer<Integer> shutdownAction) {
        this.id = id;
        this.seconds = seconds;
        l = logger;
        this.shutdownAction = shutdownAction;
    }

    public int getId() {
        return id;
    }

    public void act() {
        thread = new Thread(() -> {
            startTime = LocalDateTime.now();
            l.log("timer #" + id + " started at thread " + Thread.currentThread().getName());
            try {
                Thread.sleep(seconds * 1000L);
                l.log("timer #" + id + " shoots!");
            } catch (InterruptedException e) {
                l.log("timer #" + id + " stopped!");
            } finally {
                shutdownAction.accept(id);
            }

            try {
                Thread.sleep(3000);
            }
            catch (Exception e) {
                l.log("qwerty!");
            }
        });
        thread.start();
    }

    public String status() {
        var finish = startTime.plusSeconds(seconds);
        return String.format("timer #%d, started at %s, %s seconds remain",
                id,
                finish.format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                Duration.between(LocalDateTime.now(), finish).getSeconds());
    }

    public void shutdown() {
        thread.interrupt();
    }

    public void join() {
        try {
            thread.join();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
