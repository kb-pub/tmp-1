package server.transport;

import exception.AppException;
import message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Transport {
    private final Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public Transport(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public void send(Message message) {
        try {
            if (out == null) {
                out = new ObjectOutputStream(socket.getOutputStream());
            }
            out.writeObject(message);
        }
        catch (IOException e) {
            throw new AppException(e.getMessage());
        }
    }

    public Message receive() {
        try {
            if (in == null) {
                in = new ObjectInputStream(socket.getInputStream());
            }
            return (Message) in.readObject();
        }
        catch (IOException | ClassNotFoundException | ClassCastException e) {
            throw new AppException(e.getMessage());
        }
    }
}