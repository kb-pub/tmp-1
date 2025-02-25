package app.server;

import app.Settings;
import protocol.common.transport.Transport;
import protocol.echo.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) {
        var pool = Executors.newCachedThreadPool();
        try (var serverSocket = new ServerSocket(Settings.PORT)) {
            while (true) {
                var socket = serverSocket.accept();
                pool.submit(() -> {
                    System.out.println("accepted " + socket);
                    var transport = new SocketTransport(socket, new SerializeCodec());
                    handleEchoProtocol(transport);
                });
            }
        } catch (IOException e) {
            System.out.println("FATAL ERROR: " + e);
        }
    }

    private static void handleEchoProtocol(Transport transport) {
        try {
            System.out.println("handle started");
            var request = transport.receive(EchoRequest.class);
            System.out.println("request " + request);
            var response = new EchoResponse("echo: " + request.getText());

            try { Thread.sleep(5000); } catch (InterruptedException e) {}

            transport.send(response);
            System.out.println("response send " + response);
        } catch (Exception e) {
            System.out.println("ERROR [" + transport + "]: " + e);
        } finally {
            transport.disconnect();
        }
    }
}
