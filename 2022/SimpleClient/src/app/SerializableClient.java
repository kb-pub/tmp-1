package app;

import app.message.EchoMessage;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class SerializableClient {
    public static void main(String[] args) {
        try (var socket = new Socket("localhost", 8188)) {
            echo(socket);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void echo(Socket socket) throws IOException, ClassNotFoundException {
        var socketIn = new ObjectInputStream(socket.getInputStream());
        var socketOut = new ObjectOutputStream(socket.getOutputStream());
        var consoleIn = new Scanner(System.in);
        var consoleOut = System.out;

        while ( true ) {
            consoleOut.print(">>> ");
            String text = consoleIn.nextLine();
            if ( text.strip().equalsIgnoreCase("bye") ) {
                break;
            }
            socketOut.writeObject(new EchoMessage(text));
            //socketOut.flush();
            var echo = (EchoMessage) socketIn.readObject();
            consoleOut.println("ECHO: " + echo);
        }
    }
}
