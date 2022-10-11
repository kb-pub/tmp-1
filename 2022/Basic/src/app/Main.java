package app;

import app.geometry.Point;
import app.geometry.Segment;

public class Main {
    public static void main(String[] args) throws Exception {
        Object o;
        System.out.println(new Segment(
                new Point(1,5), Point.O).toString());
    }
}
