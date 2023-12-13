package app.game;

public class GameEngine {
    private final Room nextRoom;

    public void start() {
        var hero = new Hero();
        var result = Actable.Result.NEXT_ROOM;
        while (result != Actable.Result.EXIT) {
            result = nextRoom.act(hero);
        }
    }
}
