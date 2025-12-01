package main.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
public class Point {

  public int x;
  public int y;
  private Point parent;

  @Override
  public String toString() {
    if (parent != null) {
      return "y = " + y + " x = " + x + " parent = [ y = " + parent.y + ", x = " + parent.x + "] ";
    }
    return "y = " + y + " x = " + x;
  }
}
