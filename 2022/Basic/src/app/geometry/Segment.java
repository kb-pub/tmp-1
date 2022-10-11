package app.geometry;

public class Segment {
    private Point begin, end;

    public void setBegin(Point begin) {
        this.begin = begin;
    }

    public void setEnd(Point end) {
        this.end = end;
    }

    public Point getBegin() {
        return begin;
    }

    public Point getEnd() {
        return end;
    }

    public Segment(Point begin, Point end) {
        assert begin != null && end != null;
        this.begin = begin;
        this.end = end;
    }

    public double length() {
        return Math.sqrt(
                (begin.getX() - end.getX())*(begin.getX() - end.getX())
                + (begin.getY() - end.getY())*(begin.getY() - end.getY())
        );
    }

    @Override
    public String toString() {
        return "Segment{" +
                "begin = " + begin +
                ", end = " + end +
                ", length = " + length() +
                '}';
    }
}
