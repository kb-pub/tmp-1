package app;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.*;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static app.Util.log;

public class Main {
    public static void main(String[] args) {
        var baseDir = "/home/kb/IdeaProjects";
        var pattern = Pattern.compile("java", Pattern.CASE_INSENSITIVE);

        var queue = new ArrayBlockingQueue<Path>(128);
        var findings = new ConcurrentHashMap<Path, Set<String>>();

        var pool = Executors.newCachedThreadPool();
        try {
            var futures = new ArrayList<Future<?>>();
            var finder = new FileFinder(baseDir, queue);
            var scrapers = new ArrayList<FileScraper>();
            futures.add(pool.submit(finder));
            IntStream.range(0, 8).forEach(i -> {
                var scraper = new FileScraper(queue, findings, pattern);
                scrapers.add(scraper);
                futures.add(pool.submit(scraper));
            });

            futures.forEach(f -> {
                try {
                    var done = false;
                    while (!done) {
                        try {
                            f.get(2, TimeUnit.SECONDS);
                            done = true;
                        } catch (TimeoutException e) {
                            log("finder: %s, scrapers: %s".formatted(
                                    finder.getFileCount(),
                                    scrapers.stream().map(FileScraper::getFileCount).toList()
                            ));
                        }
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            });
            log("finder: %s, scrapers: %s".formatted(
                    finder.getFileCount(),
                    scrapers.stream().map(FileScraper::getFileCount).toList()
            ));

            log("=================================");
            log("RESULTS: ");
            log("=================================");
            findings.forEach((file, results) -> log(file + ": " + results));
        }
        finally {
            pool.shutdown();
        }
    }
}