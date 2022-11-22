package app;

import app.timer.TimerManager;

public class App {
    public static void main(String[] args) {
        var logger = new Logger();
        logger.log("Thread name: " + Thread.currentThread().getName());
        new Menu(logger, new TimerManager(logger)).loop();
    }
}
