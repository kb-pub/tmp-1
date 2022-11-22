package app.timer;

import app.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class TimerManager {
    private final Logger l;
    private int timerIdCount = 0;

    private final List<Timer> timers = new ArrayList<>();

    public TimerManager(Logger logger) {
        this.l = logger;
    }

    public void start(int seconds) {
        var id = timerIdCount++;
        var timer = new Timer(id, seconds, l, this::remove);
        timers.add(timer);
        timer.act();
    }

    public void stop(int timerId) {
        timers.stream()
                .filter(t -> t.getId() == timerId)
                .findAny()
                .ifPresentOrElse(
                        Timer::shutdown,
                        () -> l.log("no timer found with id #" + timerId));
    }

    public void status() {
        timers.forEach(t -> l.log(t.status()));
    }

    public void shutdown() {
        timers.forEach(Timer::shutdown);
        timers.forEach(Timer::join);
    }

    public void remove(int id) {
        timers.removeIf(t -> t.getId() == id);
    }
}
