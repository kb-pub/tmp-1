package client.io;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static util.Util.rethrow;

public class Console {
    private final PrintStream out = System.out;
    private final Scanner scanner = new Scanner(System.in);
    private final Reader reader = new InputStreamReader(System.in);

    public String read() {
        return scanner.nextLine().strip();
    }

    public String readPassword() {
        return read();
//        return new String(System.console().readPassword());
    }

    public void println(Object o) {
        out.println(o);
    }

    public void print(Object o) {
        out.print(o);
    }
}
