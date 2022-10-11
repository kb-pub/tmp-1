package main.message;

import java.util.*;

public abstract class Message {
    public interface Body {
        Object getPayload();
        int getSize();
        byte[] getBytes();
    }

    public static class Tag {
        private final String value;

        public Tag(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "Tag{" +
                    "value='" + value + '\'' +
                    '}';
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

    private final String header;
    private final Set<Tag> tags = new HashSet<>();

    public Message(String header, String ... tags) {
        this.header = header;
        for (var tag : tags)
            this.tags.add(new Tag(tag));
    }

    public String getHeader() {
        return header;
    }

    abstract public Body getBody();

    public Set<Tag> getTags() {
        return tags;
    }
}
