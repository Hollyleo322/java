package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Car {

  private Integer x;
  private Integer y;

  public void turnLeft() {
    if (x == 1) {
      x -= 1;
    }
  }

  public void turnRight() {
    if (x == 0) {
      x += 1;
    }
  }
}
