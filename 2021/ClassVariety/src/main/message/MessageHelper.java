package main.message;

import java.util.ArrayList;
import java.util.Comparator;

public class MessageHelper {
    public static void printTagsSorted(Message message) {
        printTagsSorted(message, false);
    }

    public static void printTagsSorted(Message message, boolean reversed) {
//        printTagsSortedImpl(message, false);
//        printTagsSortedImpl2(message, false);
//        printTagsSortedImpl3(message, false);
//        printTagsSortedImpl4(message, false);
        printTagsSortedImpl5(message, true);
    }

    public static void printTagsSortedImpl(Message message, boolean reversed) {
        var list = new ArrayList<>(message.getTags());

        class TagComparator implements Comparator<Message.Tag> {
            @Override
            public int compare(Message.Tag tag1, Message.Tag tag2) {
                return (reversed)
                        ? tag2.getValue().compareTo(tag1.getValue())
                        : tag1.getValue().compareTo(tag2.getValue());
            }
        }

        list.sort(new TagComparator());
        for (var tag : list)
            System.out.print(tag.getValue() + " ");
        System.out.println();
    }

    public static void printTagsSortedImpl2(Message message, boolean reversed) {
        var list = new ArrayList<>(message.getTags());

        var comparator = new Comparator<Message.Tag>() {
            @Override
            public int compare(Message.Tag tag1, Message.Tag tag2) {
                return (reversed)
                        ? tag2.getValue().compareTo(tag1.getValue())
                        : tag1.getValue().compareTo(tag2.getValue());
            }
        };

        System.out.println(comparator);

        list.sort(comparator);
        for (var tag : list)
            System.out.print(tag.getValue() + " ");
        System.out.println();
    }

    public static void printTagsSortedImpl3(Message message, boolean reversed) {
        var list = new ArrayList<>(message.getTags());

        list.sort(new Comparator<Message.Tag>() {
            @Override
            public int compare(Message.Tag tag1, Message.Tag tag2) {
                return (reversed)
                        ? tag2.getValue().compareTo(tag1.getValue())
                        : tag1.getValue().compareTo(tag2.getValue());
            }
        });
        for (var tag : list)
            System.out.print(tag.getValue() + " ");
        System.out.println();
    }

    public static void printTagsSortedImpl4(Message message, boolean reversed) {
        var list = new ArrayList<>(message.getTags());

        list.sort((tag1, tag2) -> (reversed)
                    ? tag2.getValue().compareTo(tag1.getValue())
                    : tag1.getValue().compareTo(tag2.getValue()));
        for (var tag : list)
            System.out.print(tag.getValue() + " ");
        System.out.println();
    }

    public static void printTagsSortedImpl5(Message message, boolean reversed) {
        var list = new ArrayList<>(message.getTags());

        list.sort(COMPARATOR::compareTags);
//        list.sort(new SmartTagComparator());
//        list.sort(new SomeUtilityClass());

        for (var tag : list)
            System.out.print(tag.getValue() + " ");
        System.out.println();
    }

    static class SomeUtilityClass implements Comparator<Message.Tag> {
        SmartTagComparator comparator = COMPARATOR;
        @Override
        public int compare(Message.Tag o1, Message.Tag o2) {
            return comparator.compareTags(o1, o2);
        }
    }

    static class SmartTagComparator {
        public int compareTags(Message.Tag tag1, Message.Tag tag2) {
            return tag1.getValue().compareTo(tag2.getValue());
        }
    }

    static final SmartTagComparator COMPARATOR = new SmartTagComparator();
}
