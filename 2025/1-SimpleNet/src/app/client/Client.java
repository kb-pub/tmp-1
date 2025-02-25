package app.client;

import app.Settings;
import protocol.echo.*;

import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        var text = "";
        var scanner = new Scanner(System.in);
        var transport = new SocketTransport(
                Settings.HOST,
                Settings.PORT,
                new SerializeCodec());
        while (!"exit".equalsIgnoreCase(text)) {
            System.out.print("> ");
            text = scanner.nextLine().strip();
            try {
                transport.send(new EchoRequest(text));
                var response = transport.receive(EchoResponse.class);
                System.out.println(response.getText());
            } catch (Exception e) {
                System.out.println("ERROR: " + e);
            } finally {
                transport.disconnect();
            }
        }
    }
}
