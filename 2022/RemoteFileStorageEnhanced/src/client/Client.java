package client;

import client.io.Console;
import client.protocol.*;
import client.service.FileService;
import exception.AppException;
import settings.Settings;

import java.nio.file.Path;

import static util.Util.throwIf;

public class Client {
    private final Console console = new Console();
    private final FileService fileService = new FileService();
    private String token;

    public static void main(String[] args) {
        new Client().start();
    }

    private void start() {
        String command = null;
        do {
            try {
                console.print(">>> ");
                command = console.read();
                switch (command) {
                    case "login" -> login();
                    case "token" -> token();
                    case "list" -> list();
                    case "delete" -> delete();
                    case "upload" -> upload();
                    case "download" -> download();
                    case "exit" -> console.println("bye!");
                    default -> console.println("unrecognized command");
                }
            } catch (AppException e) {
                console.println("error: " + e.getClass().getSimpleName() +
                        (e.getMessage() != null ? ", " + e.getMessage() : ""));
            }
        }
        while (!"exit".equals(command));
    }

    private void login() {
        console.print("login (empty for default): ");
        var user = console.read();
        if (user.isBlank()) {
            user = Settings.DEFAULT_USER;
        }
        console.print("password (empty for default): ");
        var password = console.readPassword();
        if (password.isBlank()) {
            password = Settings.DEFAULT_PASSWORD;
        }
        token = new LoginRemoteOperation().login(user, password);
        console.println("login successful");
    }

    private void token() {
        console.println("token = " + token);
    }

    private void list() {
        var files = new FileListRemoteOperation().get(token);
        if (files.isEmpty()) {
            console.println("no files in storage");
        } else {
            console.println("files in storage:");
            files.forEach(console::println);
        }
    }

    private void delete() {
        console.print("enter filename (empty for default): ");
        var filename = console.read();
        if (filename.isBlank()) {
            filename = Settings.DEFAULT_FILE_TO_DELETE;
        }
        new FileDeleteRemoteOperation().delete(token, filename);
        console.println("file successfully deleted");
    }

    private void upload() {
        console.print("enter file name (empty for default): ");
        var filepath = console.read();
        if (filepath.isEmpty()) {
            filepath = Settings.DEFAULT_FILE_TO_UPLOAD;
        }

        var path = Path.of(filepath);
        throwIf(!fileService.exists(path), "no file found");

        var timestamp = System.currentTimeMillis();

        fileService.withInputStream(path, input -> {
            var filename = fileService.getFilename(path);
            var filesize = fileService.getFilesize(path);
            new FileUploadRemoteOperation().upload(token, filename, filesize, input);
        });

        console.println("file uploaded in " +
                (System.currentTimeMillis() - timestamp) / 1000.0 + " sec");
    }

    private void download() {
        console.print("enter filename (empty for default): ");
        var filename = console.read();
        if (filename.isBlank()) {
            filename = Settings.DEFAULT_FILE_TO_DOWNLOAD;
        }
        throwIf(filename.isBlank(), "no filename provided");
        var filepath = fileService.getFilepath(filename);
        try {
            var timestamp = System.currentTimeMillis();
            var filename0 = filename;
            fileService.withOutputStream(filepath, output -> {
                new FileDownloadRemoteOperation().download(token, filename0, output);
            });
            console.println("file downloaded in " +
                    (System.currentTimeMillis() - timestamp) / 1000.0 + " sec");
        } catch (Exception e) {
            fileService.deleteIfExists(filepath);
            throw e;
        }
    }
}
