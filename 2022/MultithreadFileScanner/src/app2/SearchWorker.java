package app2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import static app.Logger.log;

/**
 * @param
 **/
public class SearchWorker implements Runnable {
    private final Path file;
    private final Pattern regexp;
    private final ConcurrentHashMap<Path, List<String>> map;
    private final Set<SearchWorker> running;

    public SearchWorker(Path file, Pattern regexp, ConcurrentHashMap<Path, List<String>> map, Set<SearchWorker> running) {
        this.file = file;
        this.regexp = regexp;
        this.map = map;
        this.running = running;
    }

    @Override
    public void run() {
        try {
            running.add(this);
            search(file);
        }
        finally {
            running.remove(this);
        }
    }

    private void search(Path file) {
        try (var lines = Files.lines(file)) {
            lines.forEach(line -> {
                if (regexp.matcher(line).find()) {
//                    log(file + ": " + line);
                    map.computeIfAbsent(file, (k) -> new ArrayList<>()).add(line);
                }
            });
        } catch (Exception e) {
            //log("ERROR: " + file + " => " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "SearchWorker{" +
                "file=" + file +
                '}';
    }
}
