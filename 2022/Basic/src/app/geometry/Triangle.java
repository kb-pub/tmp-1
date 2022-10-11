package app.geometry;

public class Triangle {
    private Point a, b, c;
    private double perimeter;

    public Triangle(Point a, Point b, Point c) {
        assert a != null && b != null && c != null;
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public double getPerimeter() {
        return perimeter;
    }

    public double calculatePerimeter() {
        return new Segment(a, b).length() +
                new Segment(a, c).length() +
                new Segment(b, c).length();
    }

    public Segment getAB() {
        return new Segment(a, b);
    }

    public Segment getAC() {
        return new Segment(a, c);
    }

    public Segment getBC() {
        return new Segment(b, c);
    }

    private double getAngle(Segment n1, Segment n2, Segment op) {
        double near1 = n1.length(),
                near2 = n2.length(),
                opposite = op.length();
        return Math.acos( (near1*near1 + near2*near2 - opposite*opposite) / (2*near1*near2) );
    }

    public double getRadianAngleA() {
        return getAngle(getAB(), getAC(), getBC());
    }

    public double getDegreeAngleA() {
        return getRadianAngleA() / Math.PI * 180;
    }

    public double getRadianAngleB() {
        return getAngle(getAB(), getBC(), getAC());
    }

    public double getDegreeAngleB() {
        return getRadianAngleB() / Math.PI * 180;
    }

    public double getRadianAngleC() {
        return getAngle(getAC(), getBC(), getAB());
    }

    public double getDegreeAngleC() {
        return getRadianAngleC() / Math.PI * 180;
    }

    public Point getA() {
        return a;
    }

    public void setA(Point a) {
        assert a != null;
        this.a = a;
        perimeter = calculatePerimeter();
    }

    public Point getB() {
        return b;
    }

    public void setB(Point b) {
        assert b != null;
        this.b = b;
        perimeter = calculatePerimeter();
    }

    public Point getC() {
        return c;
    }

    public void setC(Point c) {
        assert c != null;
        this.c = c;
        perimeter = calculatePerimeter();
    }
}
