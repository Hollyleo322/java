package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Border {

  private int y;

  public void moveBorder() {
    this.y += 1;
  }

}
