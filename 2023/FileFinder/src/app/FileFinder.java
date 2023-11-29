package app;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.BlockingQueue;

import static app.Util.log;

public class FileFinder implements Runnable {
    public static final Path LAST_PATH = Path.of("");

    private final String baseDir;
    private final BlockingQueue<Path> queue;
    private volatile int fileCount;

    public FileFinder(String baseDir, BlockingQueue<Path> queue) {
        this.baseDir = baseDir;
        this.queue = queue;
    }

    public int getFileCount() {
        return fileCount;
    }

    @Override
    public void run() {
        var base = Path.of(baseDir);
        if (!Files.isDirectory(base)) {
            throw new AppException(base + " is not s directory");
        }
        try {
            Files.walkFileTree(base, new FileVisitor<>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    try {
                        queue.put(file);
                        fileCount++;
                    }
                    catch (InterruptedException e) {
                        log("File finder interrupted");
                        return FileVisitResult.TERMINATE;
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    log("File finder: file '%s' error - %s".formatted(file, exc));
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    if (exc != null) {
                        log("File finder: dir '%s' error - %s".formatted(dir, exc));
                        return FileVisitResult.SKIP_SUBTREE;
                    } else {
                        return FileVisitResult.CONTINUE;
                    }
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            queue.put(LAST_PATH);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log("File finder finished!");
    }
}
