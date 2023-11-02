package app;

import app.sailor.Deckhand;
import app.sailor.Navigator;
import app.sailor.Sailor;

public class Main {
    public static void main(String[] args) {
        var sailor = new Sailor(
                "test",
                "https://some.avatar/test",
                new Deckhand(1),
                new Navigator(2));
        var ava = sailor.getAvatar();
//        var ava = Sailor.getAvatar(sailor);

        for (int i = 1, fact = 1; i < 40; i++, fact *= i) {
            System.out.printf("%d -> %d\n", i, fact);
        }
    }
}
