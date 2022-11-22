package app.protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class StringEchoProtocolHandler implements Runnable {
    private final Socket socket;

    public StringEchoProtocolHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (socket) {
            System.out.println("connection: " + socket + ", " + Thread.currentThread().getName());
            var socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            var socketOut = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);
            String text;
            while ( (text = socketIn.readLine()) != null ) {
                System.out.println(socket + " input: " + text);
                socketOut.println(text + " echoed");
            }
        }
        catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
