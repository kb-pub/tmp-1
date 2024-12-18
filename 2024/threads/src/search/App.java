package search;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class App {
    private static final Map<Path, Set<String>> RESULTS = new ConcurrentHashMap<>();
    private static final ThreadPoolExecutor POOL = new ThreadPoolExecutor(
            16, 16,
            1, TimeUnit.MINUTES,
            new ArrayBlockingQueue<>(1024),
            Thread::new,
            new ThreadPoolExecutor.CallerRunsPolicy());
    private int fileCount = 0;
    private int finishedTaskCount = 0;

    public static void main(String[] args) {
        var root = Path.of("/home/kb/test");
        var regexp = Pattern.compile("java\\d+");
        var start = System.nanoTime();
        try {
            search(root, regexp);
            while (POOL.getActiveCount() != 0 || !POOL.getQueue().isEmpty()) {
                Thread.yield();
            }
            RESULTS.forEach((f, set) -> System.out.println(f + ": " + set));
            System.out.printf("time = %.3f ms", (System.nanoTime() - start) / 1000000.0);
        } finally {
            POOL.shutdown();
        }
    }

    private static void search(Path root, Pattern regexp) {
        try {
            Files.walkFileTree(root, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (Files.isRegularFile(file) && Files.isReadable(file)) {
                        POOL.submit(() -> searchInFile(file, regexp));
//                        searchInFile(file, regexp);
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void searchInFile(Path file, Pattern regexp) {
        try (var lines = Files.lines(file, StandardCharsets.ISO_8859_1)) {
            var set = lines.map(regexp::matcher)
                    .flatMap(Matcher::results)
                    .map(MatchResult::group)
                    .collect(Collectors.toSet());
            if (!set.isEmpty()) {
                RESULTS.put(file, set);
            }
        } catch (IOException e) {
            System.out.println("[ERROR in search] " + file + ": " + e);
        }
    }
}
