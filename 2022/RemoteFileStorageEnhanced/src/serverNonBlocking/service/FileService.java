package serverNonBlocking.service;

import exception.AppException;
import settings.Settings;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.function.Consumer;

public class FileService {
    public List<String> getFilesForUser(String user) {
        var dir = getUserDir(user);
        if (Files.notExists(dir)) {
            return List.of();
        } else {
            try (var stream = Files.list(dir)) {
                return stream.filter(Files::isRegularFile)
                        .map(Path::getFileName)
                        .map(Path::toString)
                        .toList();
            } catch (IOException e) {
                throw new AppException(e.getMessage(), e);
            }
        }
    }

    public Path getFilepath(String filename, String user) {
        return Path.of(getUserDir(user).toString(), filename);
    }

    private Path getUserDir(String user) {
        return Path.of(Settings.REMOTE_STORAGE_PATH, user);
    }

    public OutputStream getOutputStream(Path path) throws IOException {
        return Files.newOutputStream(path);
    }

    public boolean exists(Path filepath) {
        return Files.exists(filepath);
    }

    public void delete(Path filepath) throws IOException {
        Files.delete(filepath);
    }

    public long getFilesize(Path path) throws IOException {
        return Files.size(path);
    }

    public FileChannel getReadChannel(Path filepath) throws IOException {
        return getChannel(filepath, StandardOpenOption.READ);
    }

    private FileChannel getChannel(Path filepath, OpenOption openOption) throws IOException {
        return FileChannel.open(filepath, openOption);
    }

}
