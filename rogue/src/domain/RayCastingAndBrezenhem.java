package domain;

import java.util.Vector;
import java.util.HashSet;
import java.util.Set;
import jcurses.system.CharColor;
import jcurses.system.Toolkit;

public class RayCastingAndBrezenhem {

    public static Set<Point> useBrezenhem(Point first, Point second) {
        Set<Point> points = new HashSet<>();
        boolean steep = false;
        if (Math.abs(first.getX() - second.getX()) < Math.abs(first.getY() - second.getY())) {
            first.transpose();
            second.transpose();
            steep = true;
        }
        if (first.getX() > second.getX()) {
            first.swap(second);
        }
        int dx = second.getX() - first.getX();
        int dy = second.getY() - first.getY();
        int D = Math.abs(dy) * 2;
        int error = 0;
        int y = first.getY();
        for (int x = first.getX(); x <= second.getX(); x++) {
            if (steep) {
                points.add(new Point(y, x));
            } else {
                points.add(new Point(x, y));
            }
            error += D;
            if (error > dx) {
                y += (second.getY() > first.getY() ? 1 : -1);
                error -= dx * 2;
            }
        }
        return points;
    }

    public static Set<Point> useRayCasting(Point pos, Direction dir, Room rm) {
        Vector<Point> pointsOfEdges = new Vector<>();
        pointsOfEdges = findEdges(new Point(pos), dir, rm);
        Set<Point> unveiledPoints = new HashSet<>();
        for (Point pt : pointsOfEdges) {
            unveiledPoints.addAll(useBrezenhem(new Point(pos), pt));
        }
        return unveiledPoints;
    }

    public static Vector<Point> findEdges(Point pos, Direction dir, Room rm) {
        Vector<Point> points = new Vector<>();
        switch (dir) {
            case Up:
                do {
                    pos.setY(pos.getY() - 1);
                } while (!rm.isPointOutideRoom(pos));
                pos.setY(pos.getY() + 1);
                points.add(new Point(pos));
                pos.setX(pos.getX() - 1);
                if (!rm.isPointOutideRoom(pos)) {
                    points.add(new Point(pos));
                }
                pos.setX(pos.getX() + 2);
                if (!rm.isPointOutideRoom(pos)) {
                    points.add(new Point(pos));
                }
                break;
            case Down:
                do {
                    pos.setY(pos.getY() + 1);
                } while (!rm.isPointOutideRoom(pos));
                pos.setY(pos.getY() - 1);
                points.add(new Point(pos));
                pos.setX(pos.getX() - 1);
                if (!rm.isPointOutideRoom(pos)) {
                    points.add(new Point(pos));
                }
                pos.setX(pos.getX() + 2);
                if (!rm.isPointOutideRoom(pos)) {
                    points.add(new Point(pos));
                }
                break;
            case Left:
                pos.setX(pos.getX() - 4);
                points.add(new Point(pos));
                pos.setY(pos.getY() - 1);
                if (!rm.isPointOutideRoom(pos)) {
                    points.add(new Point(pos));
                }
                pos.setY(pos.getY() + 2);
                if (!rm.isPointOutideRoom(pos)) {
                    points.add(new Point(pos));
                }
                break;
            case Right:
                pos.setX(pos.getX() + 4);
                points.add(new Point(pos));
                pos.setY(pos.getY() - 1);
                if (!rm.isPointOutideRoom(pos)) {
                    points.add(new Point(pos));
                }
                pos.setY(pos.getY() + 2);
                if (!rm.isPointOutideRoom(pos)) {
                    points.add(new Point(pos));
                }
                break;
        }
        return points;
    }
}
