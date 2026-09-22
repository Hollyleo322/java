package main.demo.model;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import main.demo.painter.FxPainter;

@AllArgsConstructor
public class AutoGen implements Runnable {

  private Cave cave;
  private Cave previousCave;
  private FxPainter fxPainter;
  private int interval;

  @Override
  public void run() {
    previousCave = new Cave(new int[cave.getRows()][cave.getCols()], cave.getRows(), cave.getCols(),
        cave.getLimitBirth(),
        cave.getLimitDeath());
    do {
      exchange();
      cave.generateNextGen();
      fxPainter.drawCave(cave);
      try {
        Thread.sleep(interval);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    } while (!isEqualsField());
  }

  private boolean isEqualsField() {
    boolean result = true;
    for (int i = 0; i < cave.getRows(); i++) {
      for (int j = 0; j < cave.getCols(); j++) {
        if (cave.getField()[i][j] != previousCave.getField()[i][j]) {
          return false;
        }
      }
    }
    return result;
  }

  private void exchange() {
    for (int i = 0; i < cave.getRows(); i++) {
      for (int j = 0; j < cave.getCols(); j++) {
        previousCave.getField()[i][j] = cave.getField()[i][j];
      }
    }
  }
}
