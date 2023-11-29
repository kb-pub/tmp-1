package app.game.io;

public class ConsoleIO implements IO {
    @Override
    public void print(Object o) {
        System.out.println(o);
    }
}
