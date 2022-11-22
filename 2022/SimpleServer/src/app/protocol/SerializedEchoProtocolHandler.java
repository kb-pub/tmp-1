package app.protocol;

import app.message.EchoMessage;

import java.io.*;
import java.net.Socket;

public class SerializedEchoProtocolHandler implements Runnable {
    private final Socket socket;

    public SerializedEchoProtocolHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (socket) {
            System.out.println("connection: " + socket + ", " + Thread.currentThread().getName());
            var socketOut = new ObjectOutputStream(socket.getOutputStream());
            var socketIn = new ObjectInputStream(socket.getInputStream());
            EchoMessage text;
            while ( (text = (EchoMessage) socketIn.readObject()) != null ) {
                System.out.println(socket + " input: " + text);
                socketOut.writeObject(new EchoMessage(
                        text.getText() + " echoed"));
            }
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
