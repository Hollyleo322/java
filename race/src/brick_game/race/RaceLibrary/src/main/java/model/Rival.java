package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Rival {

  private List<Car> rivals;

  public Rival() {
    rivals = new ArrayList<>();
  }

  public void createRival() {
    Random random = new Random();
    int x = random.nextInt(2);
    rivals.add(new Car(x, 0));
  }

}
