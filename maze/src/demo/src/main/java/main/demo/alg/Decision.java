package main.demo.alg;

import main.demo.model.Point;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import lombok.Getter;
import main.demo.model.Field;

public class Decision {

  Deque<Point> deque;
  List<Point> visited;
  @Getter
  List<Point> path;

  public Decision() {
    deque = new ArrayDeque<>();
    visited = new ArrayList<>();
    path = new ArrayList<>();
  }

  public void findDecision(Field field, Point start, Point end) {
    deque.add(start);
    Point current = null;
    while (!deque.isEmpty()) {
      current = deque.pop();
      visited.add(current);
      if (current.x == end.x && current.y == end.y) {
        break;
      }
      addNeighbours(field, current);
    }
    while (current.getParent() != null) {
      path.add(current);
      current = current.getParent();
    }
  }

  private void addNeighbours(Field field, Point current) {
    addLeftPoint(field, current);
    addRightPoint(field, current);
    addUpperPoint(field, current);
    addLowerPoint(field, current);
  }

  private void addLeftPoint(Field field, Point current) {
    if (current.x - 1 >= 0 && field.getVertical()[current.y][current.x - 1] == 0 && !contains(
        new Point(
            current.x - 1, current.y, null))) {
      deque.add(new Point(current.x - 1, current.y, current));
    }
  }

  private void addRightPoint(Field field, Point current) {
    if (current.x + 1 < field.getCols() && field.getVertical()[current.y][current.x] == 0
        && !contains(new Point(
        current.x + 1, current.y, null))) {
      deque.add(new Point(current.x + 1, current.y, current));
    }
  }

  private void addUpperPoint(Field field, Point current) {
    if (current.y - 1 >= 0 && field.getHorizontal()[current.y - 1][current.x] == 0 && !contains(
        new Point(
            current.x, current.y - 1, null))) {
      deque.add(new Point(current.x, current.y - 1, current));
    }
  }

  private void addLowerPoint(Field field, Point current) {
    if (current.y + 1 < field.getRows() && field.getHorizontal()[current.y][current.x] == 0
        && !contains(new Point(
        current.x, current.y + 1, null))) {
      deque.add(new Point(current.x, current.y + 1, current));
    }
  }

  private boolean contains(Point pt) {
    boolean result = false;
    for (Point it : visited) {
      if (it.y == pt.y && it.x == pt.x) {
        result = true;
        break;
      }
    }
    return result;
  }
}
