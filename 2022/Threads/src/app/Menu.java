package app;

import app.exception.AppException;
import app.exception.CommandParseException;
import app.timer.TimerManager;

import java.util.Arrays;
import java.util.Scanner;

public class Menu {
    private final Logger l;
    private final TimerManager timerManager;

    public Menu(Logger l, TimerManager timerManager) {
        this.l = l;
        this.timerManager = timerManager;
    }

    public void loop() {
        var scanner = new Scanner(System.in);
        String command = "";
        do {
            try {
                l.lognr(">> ");
                var parts = scanner.nextLine().strip()
                        .toLowerCase().split("\\s+");

                command = parts[0];
                if (command.isEmpty())
                    continue;

                switch (command) {
                    case "start":
                        performStart(parts);
                        break;
                    case "stop":
                        performStop(parts);
                        break;
                    case "status":
                        performStatus();
                        break;
                    case "exit":
                        performExit();
                        break;
                    default:
                        throw new CommandParseException("command not recognized!");
                }
            } catch (AppException e) {
                l.log(e.getMessage());
            }
        }
        while (!"exit".equals(command));
    }

    private void performStart(String [] params) {
        if (params.length != 2)
            throw new CommandParseException("USAGE: start <number_of_seconds>");
        var seconds = params[1];
        if ( ! seconds.matches("\\d+"))
            throw new CommandParseException("USAGE: start <number_of_seconds>");

        timerManager.start(Integer.parseInt(seconds));
    }

    private void performStop(String [] params) {
        if (params.length != 2)
            throw new CommandParseException("USAGE: stop <timer_id>");
        var timerId = params[1];
        if ( ! timerId.matches("\\d+"))
            throw new CommandParseException("USAGE: stop <timer_id>");

        timerManager.stop(Integer.parseInt(timerId));
    }

    private void performStatus() {
        timerManager.status();
    }

    private void performExit() {
        timerManager.shutdown();
        l.log("goodbye!");
    }
}
