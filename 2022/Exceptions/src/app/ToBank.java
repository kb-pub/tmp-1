package app;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class ToBank {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            throw new IllegalArgumentException("USAGE: ...");
        }
        var logDirPath = args[0];
        if (! Files.isDirectory(Path.of(logDirPath))) {
            System.out.println("No log dir found");
            throw new FileNotFoundException();
        }

        var datetime = DateTimeFormatter.ofPattern("yyyy-MM-dd_hh-mm-ss")
                .format(LocalDateTime.now());
        var logPath = Path.of(logDirPath, "log_" + datetime + ".log");

        try (var log = new PrintWriter(logPath.toString())) {
            log.println("hello from app");
        }

        var rand = new Random();
        var simulationTime = LocalDateTime.parse("2022-01-01T00:00");
        simulationTime = simulationTime.plusHours(rand.nextInt(5 * 24));
        var endTime = LocalDateTime.parse("2023-01-01T00:00");
        while (simulationTime.isBefore(endTime)) {
            // ...
        }

    }
}
