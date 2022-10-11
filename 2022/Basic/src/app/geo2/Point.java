package app.geo2;

public class Point {
    public final static Point O = new Point(0, 0);

    private double x, y;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(double xy) {
        this(xy, xy);
    }

    public Point() {
        x = y = 0;
    }

    public String toString() {
        return String.format("(%s, %.2f)", x, y);
//        return "(" + this.x + ", " + this.y + ")";
    }
}
