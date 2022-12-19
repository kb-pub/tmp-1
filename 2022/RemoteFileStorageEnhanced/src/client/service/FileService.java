package client.service;

import exception.AppException;
import settings.Settings;
import util.Util;

import java.io.*;
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

    public Path getFilepath(String filename) {
        return Path.of(Settings.LOCAL_STORAGE_PATH, filename);
    }

    public void deleteIfExists(Path filepath) {
        Util.rethrow(() -> {
            Files.deleteIfExists(filepath);
        });
    }

    public void withInputStream(Path path, Consumer<InputStream> action) {
        try (var stream = new BufferedInputStream(Files.newInputStream(path))) {
            action.accept(stream);
        } catch (IOException e) {
            throw new AppException(e.getMessage(), e);
        }
    }

    public void withOutputStream(Path path, Consumer<OutputStream> action) {
        try (var stream = new BufferedOutputStream(Files.newOutputStream(path))) {
            action.accept(stream);
        } catch (IOException e) {
            throw new AppException(e.getMessage(), e);
        }
    }
}
