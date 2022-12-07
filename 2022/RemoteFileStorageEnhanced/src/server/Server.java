package server;

import message.serialization.MessageSerializerFactory;
import server.transport.TransportFactory;
import server.protocol.Protocol;
import server.service.FileService;
import server.service.UserService;
import server.transport.SocketTransport;
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
