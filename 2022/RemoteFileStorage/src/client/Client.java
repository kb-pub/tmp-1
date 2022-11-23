package client;

import client.io.Console;
import client.protocol.FileListHandler;
import client.protocol.LoginHandler;
import client.transport.Transport;
import exception.AppException;

public class Client {
    private final Console console = new Console();
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
                    case "exit" -> console.println("bye!");
                    default -> console.println("unrecognized command");
                }
            } catch (AppException e) {
                console.println("error: " + e.getMessage());
            }
        }
        while (!"exit".equals(command));
    }

    private void login() {
        console.print("login: ");
        var user = console.read();
        console.print("password: ");
        var password = console.read();
        token = Transport.withinConnection(transport ->
                new LoginHandler(transport).login(user, password));
        console.println("login successful");
    }

    private void token() {
        console.println("token = " + token);
    }

    private void list() {
        var files = Transport.withinConnection(transport ->
                new FileListHandler(transport).get(token));
        if (files.isEmpty()) {
            console.println("no files in storage");
        }
        else {
            console.println("files in storage:");
            files.forEach(console::println);
        }
    }
}
