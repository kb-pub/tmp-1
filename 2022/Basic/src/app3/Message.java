package app3;

import java.util.Locale;
import java.util.Objects;

public class Message {
    private final static Body EMPTY_BODY = new EmptyBody();

    private Body body = EMPTY_BODY;
    private Tag tag = new Tag("");

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public String getStringToSort() {
        return  getTag().getValue();
    }

    public interface Body {
        String asString();
        String asHtml();
    }

    public static class Tag {
        private final String value;

        public Tag(String value) {
            if (value == null)
                throw new NullPointerException();
            value = value
                    .toLowerCase()
                    .replaceAll("[^a-zа-я]", "");
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Tag tag = (Tag) o;
            return Objects.equals(value, tag.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

    private static class EmptyBody implements Body {
        @Override
        public String asString() {
            return "";
        }

        @Override
        public String asHtml() {
            return "<p></p>";
        }
    }
}
