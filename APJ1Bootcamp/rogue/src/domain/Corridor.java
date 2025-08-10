package domain;

import java.util.Vector;

public class Corridor {
    private Vector<Point> _points;
    private Vector<Integer> _unveiled;

    private void makeVerticalCorridor(Point it, Point end) {
        int middle = ((end.getY() - it.getY()) / 2) + it.getY();
        if (it.getX() == end.getX()) {
            while (it.getY() != end.getY()) {
                it.setY(it.getY() + 1);
                _points.add(new Point(it.getX(), it.getY()));
            }
        } else if (it.getX() > end.getX()) {
            while (it.getY() != middle) {
                it.setY(it.getY() + 1);
                _points.add(new Point(it.getX(), it.getY()));
            }
            while (it.getX() != end.getX()) {
                it.setX(it.getX() - 1);
                _points.add(new Point(it.getX(), it.getY()));
            }
            while (it.getY() != end.getY()) {
                it.setY(it.getY() + 1);
                _points.add(new Point(it.getX(), it.getY()));
            }
        } else if (it.getX() < end.getX()) {
            while (it.getY() != middle) {
                it.setY(it.getY() + 1);
                _points.add(new Point(it.getX(), it.getY()));
            }
            while (it.getX() != end.getX()) {
                it.setX(it.getX() + 1);
                _points.add(new Point(it.getX(), it.getY()));
            }
            while (it.getY() != end.getY()) {
                it.setY(it.getY() + 1);
                _points.add(new Point(it.getX(), it.getY()));
            }
        }
    }

    private void makeHorizontalCorridor(Point it, Point end) {
        int middle = ((end.getX() - it.getX()) / 2) + it.getX();
        if (it.getY() == end.getY()) {
            while (it.getX() != end.getX()) {
                it.setX(it.getX() + 1);
                _points.add(new Point(it.getX(), it.getY()));
            }
        } else if (it.getY() < end.getY()) {
            while (it.getX() != middle) {
                it.setX(it.getX() + 1);
                _points.add(new Point(it.getX(), it.getY()));
            }
            while (it.getY() != end.getY()) {
                it.setY(it.getY() + 1);
                _points.add(new Point(it.getX(), it.getY()));
            }
            while (it.getX() != end.getX()) {
                it.setX(it.getX() + 1);
                _points.add(new Point(it.getX(), it.getY()));
            }
        } else if (it.getY() > end.getY()) {
            while (it.getX() != middle) {
                it.setX(it.getX() + 1);
                _points.add(new Point(it.getX(), it.getY()));
            }
            while (it.getY() != end.getY()) {
                it.setY(it.getY() - 1);
                _points.add(new Point(it.getX(), it.getY()));
            }
            while (it.getX() != end.getX()) {
                it.setX(it.getX() + 1);
                _points.add(new Point(it.getX(), it.getY()));
            }
        }
    }

    public Corridor() {
        _points = new Vector<>();
        _unveiled = new Vector<>();
    }

    public Corridor(Point start, Point end, boolean vertical) {
        _points = new Vector<>();
        _unveiled = new Vector<>();
        Point it = start;
        if (vertical) {
            makeVerticalCorridor(it, end);
        } else {
            makeHorizontalCorridor(it, end);
        }
    }

    public Vector<Point> getPoints() {
        return _points;
    }

    public Vector<Integer> getUnveiled() {
        return _unveiled;
    }

    public void addToUnveiled(int index) {
        _unveiled.add(index);
        if (_unveiled.contains(index + 1)) {
            _unveiled.add(index - 1);
        } else {
            _unveiled.add(index + 1);
        }
    }

    public boolean isPointOutideCorridor(Point npos) {
        boolean res = true;
        for (Point point : _points) {
            if (point.getX() == npos.getX() && point.getY() == npos.getY()) {
                res = false;
                break;
            }
        }
        return res;
    }
}
