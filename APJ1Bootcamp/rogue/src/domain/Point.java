package domain;

public class Point {
    private int _x;
    private int _y;

    public Point() {
        _x = 0;
        _y = 0;
    }

    public Point(int x, int y) {
        _x = x;
        _y = y;
    }

    public Point(Point other) {
        _x = other._x;
        _y = other._y;
    }

    public void setX(int newX) {
        _x = newX;
    }

    public void setY(int newY) {
        _y = newY;
    }

    public int getX() {
        return _x;
    }

    public int getY() {
        return _y;
    }

    public void swap(Point second) {
        int tmpX = _x;
        int tmpY = _y;
        _x = second.getX();
        _y = second.getY();
        second.setX(tmpX);
        second.setY(tmpY);
    }

    public void transpose() {
        int tmp = _x;
        _x = _y;
        _y = tmp;
    }
}
