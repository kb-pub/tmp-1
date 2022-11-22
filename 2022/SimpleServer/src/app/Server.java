package app;

import app.protocol.SerializedEchoProtocolHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static ExecutorService pool = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        try (var ss = new ServerSocket(8188)) {
            while (true) {
                pool.submit(new SerializedEchoProtocolHandler(ss.accept()));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            pool.shutdown();
        }
    }
}
