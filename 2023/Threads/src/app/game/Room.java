package app.game;

import app.game.io.IO;

import java.util.Collection;
import java.util.List;

public class Room implements Actable {
    private final IO io;
    private final Doors doors;
    private final List<Actable> actables;
    private final String commonDescription;

    public Room(IO io, Doors doors, List<Actable> actables, String commonDescription) {
        this.io = io;
        this.doors = doors;
        this.actables = actables;
        this.commonDescription = commonDescription;
    }

    @Override
    public Result act(Hero hero) {
        var exit = false;
        while (!exit) {
            io.print(commonDescription);
            actables.forEach(io::print);
            int choice = io.readInt();
            var result = actables.get(choice).act(hero);
            if (result == Result.EXIT) {
                exit = true;
            }
        }
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }
}
