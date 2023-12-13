package filefinder;

import ui.Logger;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.BlockingQueue;

public class FileFinder implements Runnable {
    public static final Path LAST_PATH = Path.of("");

    private final Path baseDir;
    private final BlockingQueue<Path> queue;
    private final Logger logger;
    private volatile int fileCount;

    public FileFinder(Path baseDir, BlockingQueue<Path> queue, Logger logger) {
        this.baseDir = baseDir;
        this.queue = queue;
        this.logger = logger;
    }

    public int getFileCount() {
        return fileCount;
    }

    @Override
    public void run() {
        try {
            Files.walkFileTree(baseDir, new FileVisitor<>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (Thread.currentThread().isInterrupted()) {
                        return FileVisitResult.TERMINATE;
                    }

                    try {
                        queue.put(file);
                        fileCount++;
                    }
                    catch (InterruptedException e) {
                        logger.log("File finder interrupted");
                        return FileVisitResult.TERMINATE;
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    logger.log("File finder: file '%s' error - %s".formatted(file, exc));
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    if (exc != null) {
                        logger.log("File finder: dir '%s' error - %s".formatted(dir, exc));
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
            /* do nothing */
//            e.printStackTrace();
        }

        logger.log("File finder finished!");
    }
}
