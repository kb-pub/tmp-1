package app;

import app.geometry.Point;
import app.geometry.Segment;
import app.geometry.Triangle;

public class Test {

    public void tests() {
        segmentTest();
//        triangleTest();
    }

    private void segmentTest() {
        assert new Segment(new Point(1, 1), new Point(2, 2)).length() == Math.sqrt(2);
    }

//    private void triangleTest() {
//        assert new Triangle(new Point(), new Point(0, 3), new Point(4, 0)).getP() == 12;
//    }
}
