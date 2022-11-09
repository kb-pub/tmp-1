package app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import static app.Logger.log;

/**
 * @param
 **/
public class SearchWorker implements Runnable {
    private final BlockingQueue<Path> queue;
    private final Pattern regexp;
    private final ConcurrentHashMap<Path, List<String>> map;

    public SearchWorker(BlockingQueue<Path> queue, Pattern regexp, ConcurrentHashMap<Path, List<String>> map) {
        this.queue = queue;
        this.regexp = regexp;
        this.map = map;
    }

    @Override
    public void run() {
        Path file = null;
        try {
            while ((file = queue.take()) != App.FINISH_STUB) {
                search(file);
            }
            queue.put(App.FINISH_STUB);
            log(Thread.currentThread().getName() + " finished");
        } catch (Exception e) {
            log("ERROR: " + file);
            throw new RuntimeException(e);
        }
    }

    private void search(Path file) {
        try (var lines = Files.lines(file)) {
            lines.forEach(line -> {
                if (regexp.matcher(line).find()) {
                    log(file + ": " + line);
                    map.computeIfAbsent(file, (k) -> new ArrayList<>()).add(line);
                }
            });
        } catch (Exception e) {
            //log("ERROR: " + file + " => " + e.getMessage());
        }
    }
}
