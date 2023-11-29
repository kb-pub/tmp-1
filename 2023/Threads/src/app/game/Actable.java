package app.game;

public interface Actable {
    enum Result {CONTINUE, NEXT_ROOM, EXIT}
    Result act(Hero hero);
    String getDescription();
}
