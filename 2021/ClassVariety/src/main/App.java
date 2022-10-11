package main;

import main.message.Message;
import main.message.MessageHelper;
import main.message.SimpleTextMessage;

public class App {
    public static void main(String[] args) {
        test3();
    }

    static void test(SimpleTextMessage message) {
        Message.Body body = message.getBody();
        var textBody = message.new TextBody();
        System.out.println(textBody.getPayload());
        var tag = new Message.Tag("test");
    }

    static void test2() {
        var message = new SimpleTextMessage("test message", "tag1", "tag2", "tag3", "tag1");
        System.out.println(message.getBody().getPayload());
        for (Message.Tag tag : message.getTags())
            System.out.println(tag);
    }

    static void test3() {
        var message = new SimpleTextMessage("test message", "tag1", "tag2", "tag3", "tag11");
        MessageHelper.printTagsSorted(message, false);
    }
}
