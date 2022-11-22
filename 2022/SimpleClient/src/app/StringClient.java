package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class StringClient {
    public static void main(String[] args) {
        try (var socket = new Socket("localhost", 8188)) {
            echo(socket);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void echo(Socket socket) throws IOException {
        var socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        var socketOut = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);
        var consoleIn = new Scanner(System.in);
        var consoleOut = new PrintWriter(System.out, true);

        while ( true ) {
            consoleOut.print(">>> ");
            consoleOut.flush();
            String text = consoleIn.nextLine();
            if ( text.strip().equalsIgnoreCase("bye") ) {
                break;
            }
            socketOut.println(text);
            socketOut.flush();
            var echo = socketIn.readLine();
            consoleOut.println("ECHO: " + echo);
            consoleOut.flush();
        }
    }
}
