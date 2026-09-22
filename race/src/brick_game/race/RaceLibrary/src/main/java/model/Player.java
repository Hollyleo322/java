package model;

import lombok.Getter;

@Getter
public class Player {

  private final Car car;

  public Player() {
    car = new Car(0, 16);

  }

  public void turnLeft() {
    car.turnLeft();
  }

  public void turnRight() {
    car.turnRight();
  }
}
