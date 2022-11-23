package server.service;

import exception.AppException;
import settings.Settings;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class FileService {
    public List<String> getFilesForUser(String user) {
        var dir = getUserDir(user);
        if (Files.notExists(dir)) {
            return List.of();
        }
        else {
            try (var stream = Files.list(dir)) {
                return stream.filter(Files::isRegularFile)
                        .map(Path::getFileName)
                        .map(Path::toString)
                        .toList();
            }
            catch (IOException e) {
                throw new AppException(e.getMessage());
            }
        }
    }

    private Path getUserDir(String user) {
        return Path.of(Settings.REMOTE_STORAGE_PATH, user);
    }
}
