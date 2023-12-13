package filefinder;

import ui.Logger;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FileFinderTask implements Runnable {
    private final Path baseDir;
    private final Pattern pattern;
    private final int workThreadCount;
    private final Logger logger;
    private Thread workThread;

    public FileFinderTask(String baseDir, String regexp, int workThreadCount, Logger logger) {
        this.baseDir = Path.of(baseDir);
        if (!Files.isDirectory(this.baseDir)) {
            throw new AppException("'%s' is not a directory".formatted(this.baseDir));
        }
        this.pattern = Pattern.compile(regexp, Pattern.CASE_INSENSITIVE);
        this.workThreadCount = workThreadCount;
        this.logger = logger;
    }

    public void cancel() {
        if (workThread != null) {
            workThread.interrupt();
        }
    }

    public void run() {
        workThread = Thread.currentThread();
        var queue = new ArrayBlockingQueue<Path>(128);
        var findings = new ConcurrentHashMap<Path, Set<String>>();

        var pool = Executors.newCachedThreadPool();
        try {
            var futures = new ArrayList<Future<?>>();
            var finder = new FileFinder(baseDir, queue, logger);
            var scrapers = new ArrayList<FileScraper>();
            futures.add(pool.submit(finder));
            IntStream.range(0, workThreadCount).forEach(i -> {
                var scraper = new FileScraper(queue, findings, pattern, logger);
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
                            logger.log("finder: %s, scrapers: %s".formatted(
                                    finder.getFileCount(),
                                    scrapers.stream().map(FileScraper::getFileCount).toList()
                            ));
                        }
                    }
                }
                catch (InterruptedException e) {
                    logger.log("TASK INTERRUPTED!");
                    pool.shutdownNow();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            });
            logger.log("finder: %s, scrapers: %s".formatted(
                    finder.getFileCount(),
                    scrapers.stream().map(FileScraper::getFileCount).toList()
            ));

            logger.log("=================================");
            logger.log("RESULTS: ");
            logger.log("=================================");
            logger.log(findings.entrySet().stream()
                    .map(e -> e.getKey() + ": " + e.getValue())
                    .collect(Collectors.joining("\n")));
        }
        finally {
            pool.shutdown();
        }
    }
}