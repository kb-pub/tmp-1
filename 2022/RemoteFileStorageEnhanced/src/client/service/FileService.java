package client.service;

import exception.AppException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;

import static util.Util.rethrow;

public class FileService {
    public boolean exists(Path path) {
        return rethrow(() -> Files.exists(path));
    }

    public String getFilename(Path path) {
        return path.getFileName().toString();
    }

    public long getFilesize(Path path) {
        return rethrow(() -> Files.size(path));
    }

    public void withInputStream(Path path, Consumer<InputStream> action) {
        try (var stream = new BufferedInputStream(Files.newInputStream(path))) {
            action.accept(stream);
        } catch (IOException e) {
            throw new AppException(e.getMessage(), e);
        }
    }
}
