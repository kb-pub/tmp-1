package serverBlocking;

import serverBlocking.transport.TransportFactory;
import serverBlocking.protocol.Protocol;
import serverBlocking.service.FileService;
import serverBlocking.service.UserService;
import settings.Settings;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final ExecutorService pool = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        var userService = new UserService();
        var fileService = new FileService();
        try (var serverSocket = new ServerSocket(Settings.SERVER_PORT)) {
            while (true) {
                var socket = serverSocket.accept();
                System.out.println(socket + " connected");
                pool.submit(() -> new Protocol(TransportFactory.get(socket),
                        userService, fileService).handle());
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }
    }
}
