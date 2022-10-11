package app3;

public class App {
    public static void main(String[] args) {
        var msg = new Message();
        System.out.println("message content: " + msg.getBody().asString());
    }
}
