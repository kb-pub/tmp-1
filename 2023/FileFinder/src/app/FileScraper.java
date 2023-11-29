package app;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static app.Util.log;

public class FileScraper implements Runnable {
    private final BlockingQueue<Path> queue;
    private final ConcurrentHashMap<Path, Set<String>> findings;
    private final Pattern pattern;
    private volatile int fileCount;

    public FileScraper(BlockingQueue<Path> queue, ConcurrentHashMap<Path, Set<String>> findings, Pattern pattern) {
        this.queue = queue;
        this.findings = findings;
        this.pattern = pattern;
    }

    public int getFileCount() {
        return fileCount;
    }

    @Override
    public void run() {
        Path file;
        try {
            while ((file = queue.take()) != FileFinder.LAST_PATH) {
                fileCount++;
                try (var lines = Files.lines(file, StandardCharsets.ISO_8859_1)) {
                    var file0 = file;
                    lines.map(pattern::matcher)
                            .flatMap(Matcher::results)
                            .forEach(result -> findings
                                    .computeIfAbsent(file0, f -> new HashSet<>())
                                    .add(result.group()));
                } catch (Exception e) {
                    log("File scraper: '%s' error - %s".formatted(file, e));
                }
            }
            queue.put(FileFinder.LAST_PATH);
            log(Thread.currentThread() + " scraper finished");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
