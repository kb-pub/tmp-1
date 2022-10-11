package app3.message;

import app3.Message;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MessageHelper {
    public static List<Message> sortByTag(List<Message> messages,
                                          Message.Tag... tags) {
//        class MessageTagComparator implements Comparator<Message> {
//            @Override
//            public int compare(Message o1, Message o2) {
//                System.out.println(tags.length);
//                return o1.getTag().getValue().compareTo(o2.getTag().getValue());
//            }
//        }

        if (tags.length == 0)
            return List.of();
        var result = getFilteredByTagsList(messages, tags);

        result.sort(Comparator.comparing(Message::getStringToSort));

//        result.sort((o1, o2) ->
//                o1.getTag().getValue().compareTo(o2.getTag().getValue()));

//        result.sort(new Comparator<Message>() {
//            @Override
//            public int compare(Message o1, Message o2) {
//                return o1.getTag().getValue().compareTo(o2.getTag().getValue());
//            }
//        });

//        result.sort(new MessageTagComparator());
        return result;
    }

    private static List<Message> getFilteredByTagsList(List<Message> messages,
                                                       Message.Tag... tags) {
        var result = new ArrayList<Message>();
        var tagsList = List.of(tags);
        for (var msg : messages) {
            if (tagsList.contains(msg.getTag())) {
                result.add(msg);
            }
        }
        return result;
    }

//    private static class MessageTagComparator implements Comparator<Message> {
//        @Override
//        public int compare(Message o1, Message o2) {
//            return o1.getTag().getValue().compareTo(o2.getTag().getValue());
//        }
//    }
}
