package app;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static app.Logger.log;
import static app.Util.wrap;

public class App {
    public static Path FINISH_STUB = Path.of("");
    private final int WORKER_NUMBER = 12;

    private Path dirname;
    private Pattern regexp;

    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    public void run(String[] args) throws Exception {
        getParams(args);
        var queue = new ArrayBlockingQueue<Path>(1024);
        var map = new ConcurrentHashMap<Path, List<String>>();
        var threads = createWorkers(queue, regexp, map);
        threads.forEach(Thread::start);
        performSearching(queue);
        threads.forEach(t -> wrap(t::join));
        log("program finished!");
        log("statistics: ");
        printStatistics(map);
    }

    private void getParams(String[] args) {
        if (args.length < 2)
            throw new RuntimeException("USAGE: <appname> <dir-for-searching> <regexp>");
        dirname = Path.of(args[0]);
        if (!Files.isDirectory(dirname))
            throw new RuntimeException(dirname + " not a directory");
        regexp = Pattern.compile(args[1]);
    }

    private List<Thread> createWorkers(BlockingQueue<Path> queue,
                                       Pattern regexp,
                                       ConcurrentHashMap<Path, List<String>> map) {
        return IntStream.range(0, WORKER_NUMBER)
                .mapToObj(i -> new Thread(new SearchWorker(queue, regexp, map)))
                .toList();
    }

    private void performSearching(BlockingQueue<Path> queue) throws Exception {
        Files.walkFileTree(dirname, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (Files.isRegularFile(file)) {
                    wrap(() -> queue.put(file));
                }
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

