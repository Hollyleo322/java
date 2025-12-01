package main.demo.alg;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import main.demo.model.Field;

public class EllerAlgorithm {

  public static Field getField(int rows, int cols) {
    int[][] vertical = new int[rows][cols];
    int[][] horizontal = new int[rows][cols];
    fillRight(vertical, rows, cols);
    fillBottom(horizontal, rows, cols);
    int[] arraySets = new int[cols];
    initLine(arraySets, vertical, horizontal, cols, true, 0);
    for (int i = 1; i < rows - 1; i++) {
      initLine(arraySets, vertical, horizontal, cols, false, i);
    }
    initBottomLine(arraySets, horizontal, cols, rows - 1);
    initLastLine(arraySets, vertical, cols, rows - 1);
    return new Field(vertical, horizontal, rows, cols);
  }

  public static void initLastLine(int[] arraySets, int[][] vertical, int cols, int row) {
    addRightBorder(vertical, row, cols, arraySets);
    for (int i = 0; i < cols - 1; i++) {
      if (arraySets[i] != arraySets[i + 1]) {
        if (vertical[row][i] == 1) {
          vertical[row][i] = 0;
        }
        int check = arraySets[i + 1];
        arraySets[i + 1] = arraySets[i];
        for (int j = 0; j < cols; j++) {
          if (arraySets[j] == check) {
            arraySets[j] = arraySets[i];
          }
        }
      }
    }
  }

  public static void fillRight(int[][] array, int rows, int cols) {
    for (int i = 0; i < rows; i++) {
      array[i][cols - 1] = 1;
    }
  }

  public static void fillBottom(int[][] array, int rows, int cols) {
    for (int i = 0; i < cols; i++) {
      array[rows - 1][i] = 1;
    }
  }

  public static void initLine(int[] arraySets, int[][] vertical, int[][] horizontal, int cols,
      boolean first, int row) {
    if (first) {
      initFirstLine(arraySets, cols);
    } else {
      initBottomLine(arraySets, horizontal, cols, row);
    }
    addRightBorder(vertical, row, cols, arraySets);
    addBottomBorder(horizontal, row, cols, arraySets);
  }

  public static void initBottomLine(int[] arraySets, int[][] horizontal, int cols, int row) {
    int counter = 100;
    for (int i = 0; i < cols; i++) {
      if (horizontal[row - 1][i] == 1) {
        arraySets[i] = counter;
        counter += 1;
      }
    }
    List<Integer> current = new ArrayList<>();
    for (int i = 0; i < cols; i++) {
      if (arraySets[i] < 100) {
        current.add(arraySets[i]);
      }
    }
    int rewrite = 0;
    rewrite = incrementNumber(current, rewrite);
    for (int i = 0; i < cols; i++) {
      if (arraySets[i] >= 100) {
        arraySets[i] = rewrite;
        current.add(arraySets[i]);
        rewrite = incrementNumber(current, rewrite);
      }
    }
  }

  public static List<Integer> getIndexes(int[] arraySets) {
    List<Integer> result = new ArrayList<>();
    Set<Integer> values = new HashSet<>();
    List<Integer> tmp = new ArrayList<>();
    Random rnd = new Random();
    for (int value : arraySets) {
      values.add(value);
    }
    for (int value : values) {
      for (int i = 0; i < arraySets.length; i++) {
        if (value == arraySets[i]) {
          tmp.add(i);
        }
      }
      result.add(tmp.get(rnd.nextInt(tmp.size())));
      tmp.clear();
    }
    return result;
  }

  public static Integer incrementNumber(List<Integer> current, int rewrite) {
    while (current.contains(rewrite)) {
      rewrite += 1;
    }
    return rewrite;
  }

  public static void initFirstLine(int[] arraySets, int cols) {
    for (int i = 0; i < cols; i++) {
      arraySets[i] = i;
    }
  }

  public static void addRightBorder(int[][] vertical, int row, int cols, int[] arraySets) {
    Random rnd = new Random();
    for (int i = 0; i < cols - 1; i++) {
      if (arraySets[i] == arraySets[i + 1]) {
        vertical[row][i] = 1;
      } else {
        if (rnd.nextBoolean()) {
          vertical[row][i] = 1;
        } else {
          int check = arraySets[i + 1];
          arraySets[i + 1] = arraySets[i];
          for (int j = 0; j < cols; j++) {
            if (arraySets[j] == check) {
              arraySets[j] = arraySets[i];
            }
          }
        }
      }
    }
  }

  public static void addBottomBorder(int[][] horizontal, int row, int cols, int[] arraySets) {
    List<Integer> mustNotHaveBorder = getIndexes(arraySets);
    Random rnd = new Random();
    for (int i = 0; i < cols; i++) {
      if (rnd.nextBoolean()) {
        if (!mustNotHaveBorder.contains(i)) {
          horizontal[row][i] = 1;
        }
      }
    }
  }
}
