package app3.message;

import app3.Message;

public class SimpleStringBody implements Message.Body {
    private final String content;

    public SimpleStringBody(String content) {
        this.content = content;
    }

    @Override
    public String asString() {
        return content;
    }

    @Override
    public String asHtml() {
        return  "<p>" + toHtml(content) + "</p>";
    }

    // TODO implement!
    private String toHtml(String value) {
        return value;
    }
}
