package app2;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.concurrent.*;
import java.util.regex.Pattern;

import static app.Logger.log;
import static app.Util.wrap;

public class App {
    public static Path FINISH_STUB = Path.of("");
    private final int WORKER_NUMBER = 12;

    private Path dirname;
    private Pattern regexp;

    private final ThreadPoolExecutor pool = new ThreadPoolExecutor(
            10, 100,
            1, TimeUnit.MINUTES,
            new ArrayBlockingQueue<Runnable>(1024),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );
//    private final ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newCachedThreadPool();

    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    public void run(String[] args) throws Exception {
        getParams(args);
        var queue = new ArrayBlockingQueue<Path>(1024);
        var map = new ConcurrentHashMap<Path, List<String>>();

        startDiagnosticsThread();

        performSearching(queue, map);

        pool.shutdown();
        pool.awaitTermination(Integer.MAX_VALUE, TimeUnit.DAYS);

        log("program finished!");
        log("statistics: ");
        printStatistics(map);
    }

    private void startDiagnosticsThread() {
        var thread = new Thread(() -> {
            while (true) {
                wrap(() -> Thread.sleep(1000));
                log("active: = " + pool.getActiveCount());
                log("pool size: " + pool.getPoolSize());
                log(pool.isTerminated() + ", " + pool.isTerminating());
                log("queue size: " + pool.getQueue().size());
                log("============");
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    private void getParams(String[] args) {
        if (args.length < 2)
            throw new RuntimeException("USAGE: <appname> <dir-for-searching> <regexp>");
        dirname = Path.of(args[0]);
        if (!Files.isDirectory(dirname))
            throw new RuntimeException(dirname + " not a directory");
        regexp = Pattern.compile(args[1]);
    }

    private void performSearching(BlockingQueue<Path> queue,
                                  ConcurrentHashMap<Path, List<String>> map) throws Exception {
        Files.walkFileTree(dirname, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                pool.submit(new SearchWorker(file, regexp, map));
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                log(exc);
                return FileVisitResult.SKIP_SUBTREE;
            }
        });
        queue.put(FINISH_STUB);
    }

    private void printStatistics(ConcurrentHashMap<Path, List<String>> map) {
        log("files: " + map.mappingCount());
        log("total: " + map.values().stream().mapToInt(List::size).sum());
    }
}

