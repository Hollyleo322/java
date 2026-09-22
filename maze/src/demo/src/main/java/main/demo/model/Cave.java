package main.demo.model;

import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class Cave {

  private int[][] field;
  private int rows;
  private int cols;
  private int limitBirth;
  private int limitDeath;

  public void initWithChance(int chancePercent) {
    Random random = new Random();
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (field[i][j] == 1) {
          if (random.nextInt(101) > chancePercent) {
            field[i][j] = 0;
          }
        }
      }
    }
  }

  public void generateNextGen() {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (field[i][j] == 1) {
          if (countAlive(i, j) < limitDeath) {
            field[i][j] = 0;
          }
        } else {
          if (countAlive(i, j) > limitBirth) {
            field[i][j] = 1;
          }
        }
      }
    }

  }

  public int countAlive(int i, int j) {
    int result = 0;
    result += checkLine(i - 1, j - 1);
    result += checkInTheMiddle(i, j);
    result += checkLine(i + 1, j - 1);
    return result;
  }

  public int checkLine(int i, int j) {
    int result = 0;
    for (int jt = j; jt < j + 3; jt++) {
      if (i < 0 || i >= rows || jt < 0 || jt >= cols) {
        result += 1;
      } else {
        result += field[i][jt];
      }
    }
    return result;
  }

  public int checkInTheMiddle(int i, int j) {
    int result = 0;
    if (j - 1 < 0) {
      result += 1;
      result += field[i][j + 1];
    } else if (j + 1 >= cols) {
      result += 1;
      result += field[i][j - 1];
    } else {
      result += field[i][j - 1];
      result += field[i][j + 1];
    }
    return result;
  }
}
