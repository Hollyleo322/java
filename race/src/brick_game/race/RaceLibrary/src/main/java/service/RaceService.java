package service;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import constant.Constants;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;
import jna.JNALib;
import jna.JNAState;
import lombok.Getter;
import model.Border;
import model.Car;
import model.Player;
import model.Rival;
import model.State;

@Getter
public class RaceService {

  private final JNAState jnaState = JNALib.INSTANCE.getStatePtr();
  private final JNAState.ByValue jnastateByValue = new JNAState.ByValue();
  private static int checkLevel = 1;
  private List<Border> borders = new ArrayList<>();

  public void free() {
    JNALib.INSTANCE.freeStructure(jnaState);
  }

  public void restart() {
    zeroField();
    resetStats();
  }

  public void createBorders() {
    borders.add(new Border(0));
  }

  private void resetStats() {
    jnaState.score = 0;
    jnaState.level = 1;
    jnaState.pause = false;
    jnaState.speed = 1;
  }

  public void setHighScoreInTheFile() {
    File result = new File("high_score.txt");
    if (!result.exists()) {
      try {
        result.createNewFile();
      } catch (IOException e) {
        System.out.println("File wasn't created");
      }
    }
    try {
      PrintWriter pw = new PrintWriter(result);
      pw.printf("%d", jnaState.high_score);
      pw.close();
    } catch (FileNotFoundException e) {
      System.out.println("File wasn't found");
    }
  }

  public void setHighScore() {
    String path = "high_score.txt";
    File file = new File(path);
    try (Scanner scanner = new Scanner(file)) {
      jnaState.high_score = scanner.nextInt();
    } catch (FileNotFoundException e) {
      System.out.println("File is not found high_score will be equals to zero");
    }
  }

  public boolean checkCollision(Rival rivals, Player player) {
    boolean res = false;
    for (Car rival : rivals.getRivals()) {
      if (Objects.equals(rival.getX(), player.getCar().getX())
          && (rival.getY() + 3) >= player.getCar()
          .getY()) {
        res = true;
      }
    }
    return res;
  }

  public void update(Rival rivals, boolean hold) {
    if (!hold) {
      jnaState.speed = jnaState.level;
    }
    rivals.getRivals().forEach(car -> {
      car.setY(car.getY() + 1);
    });
    rivals.getRivals().forEach(car -> {
      if (car.getY() > 19) {
        jnaState.score += 1;
      }
    });
    if (jnaState.score > jnaState.high_score) {
      jnaState.high_score = jnaState.score;
    }
    rivals.setRivals(rivals.getRivals().stream().filter(car -> {
      return car.getY() <= 19;
    }).collect(Collectors.toList()));
    if (!rivals.getRivals().isEmpty()
        && rivals.getRivals().get(rivals.getRivals().size() - 1).getY() == 10) {
      rivals.createRival();
    }
    updateLevel();
    updateBorders();
  }

  private void updateBorders() {
    borders.forEach(Border::moveBorder);
    if (borders.stream().anyMatch(border -> {
      return border.getY() == 5;
    })) {
      borders.add(new Border(0));
    }
    borders = borders.stream().filter(border -> {
      return border.getY() <= 19;
    }).collect(Collectors.toList());
  }

  private void updateLevel() {
    if (jnaState.score % 5 == 0 && jnaState.level < 10 && jnaState.score != 0
        && jnaState.score != checkLevel) {
      jnaState.level += 1;
      checkLevel = jnaState.score;
    }
  }

  public void setMaxSpeed() {
    jnaState.speed = 10;
  }

  public void settingData(Player player, Rival rival) {
    zeroField();
    settingCar(player.getCar());
    settingRivals(rival);
    settingBorders();
  }

  private void settingBorders() {
    for (int i = 0; i < borders.size(); i++) {
      for (int j = borders.get(i).getY(); j < Constants.HEIGHTBORDER + borders.get(i).getY(); j++) {
        writeBorder(borders.get(i).getY());
      }
    }
  }

  private void settingRivals(Rival rival) {
    rival.getRivals().forEach(this::settingCar);
  }

  private void zeroField() {
    int[] array = new int[Constants.HEIGHT];
    for (int i = 0; i < Constants.HEIGHT; i++) {
      Pointer pointer = jnaState.field.getPointer(i * Native.POINTER_SIZE);
      pointer.write(0, array, 0, Constants.WIDTH);
    }
  }

  private void writeBorder(int y) {
    Pointer pt = jnaState.field.getPointer(y * Native.POINTER_SIZE);
    int[] array = new int[Constants.WIDTH];
    pt.read(0, array, 0, Constants.WIDTH);
    int rightSide = 8;
    for (int i = 0; i < Constants.WIDTHBORDER; i++, rightSide++) {
      array[i] = 1;
      array[rightSide] = 1;
    }
    pt.write(0, array, 0, Constants.WIDTH);
  }

  private void settingCar(Car car) {
    int counter = car.getY();
    if (counter > Constants.HEIGHT - 1) {
      return;
    }
    Pointer[] arrayPointers = new Pointer[Constants.HEIGHT];
    for (int i = 0; i < Constants.HEIGHT; i++) {
      arrayPointers[i] = jnaState.field.getPointer(i * Native.POINTER_SIZE);
    }
    writeOnePoint(arrayPointers, counter, car.getX());
    counter++;
    writeThreePoints(arrayPointers, counter, car.getX());
    counter++;
    writeOnePoint(arrayPointers, counter, car.getX());
    counter++;
    writeTwoPoints(arrayPointers, counter, car.getX());
  }

  private void writeOnePoint(Pointer[] array, int counter, int x) {
    if (counter > Constants.HEIGHT - 1) {
      return;
    }
    int[] arrayDate = new int[Constants.WIDTH];
    array[counter].read(0, arrayDate, 0, Constants.WIDTH);
    if (x == 0) {
      arrayDate[3] = 1;
    } else {
      arrayDate[6] = 1;
    }
    array[counter].write(0, arrayDate, 0, Constants.WIDTH);
  }

  private void writeThreePoints(Pointer[] array, int counter, int x) {
    if (counter > Constants.HEIGHT - 1) {
      return;
    }
    int[] arrayDate = new int[Constants.WIDTH];
    array[counter].read(0, arrayDate, 0, Constants.WIDTH);
    if (x == 0) {
      for (int i = 2; i < 5; i++) {
        arrayDate[i] = 1;
      }
    } else {
      for (int i = 5; i < 8; i++) {
        arrayDate[i] = 1;
      }
    }
    array[counter].write(0, arrayDate, 0, Constants.WIDTH);
  }

  private void writeTwoPoints(Pointer[] array, int counter, int x) {
    if (counter > Constants.HEIGHT - 1) {
      return;
    }
    int[] arrayDate = new int[Constants.WIDTH];
    array[counter].read(0, arrayDate, 0, Constants.WIDTH);
    if (x == 0) {
      arrayDate[2] = 1;
      arrayDate[4] = 1;
    } else {
      arrayDate[5] = 1;
      arrayDate[7] = 1;
    }
    array[counter].write(0, arrayDate, 0, Constants.WIDTH);
  }

  public State getState() {
    var field = getArray(jnaState.field, Constants.HEIGHT, Constants.WIDTH);
    var next = getArray(jnaState.next, Constants.WIDTHFORNEXT, Constants.WIDTHFORNEXT);
    return new State(field, next, jnaState.score, jnaState.high_score, jnaState.level,
        jnaState.speed, jnaState.pause);
  }

  public boolean[][] getArray(Pointer twoDimensionalArray, int row, int col) {
    boolean[][] res = new boolean[row][col];
    int[] intArray = new int[col];
    for (int i = 0; i < row; i++) {
      Pointer pt = twoDimensionalArray.getPointer(i * Native.POINTER_SIZE);
      pt.read(0, intArray, 0, col);
      for (int j = 0; j < col; j++) {
        if (intArray[j] == 0) {
          res[i][j] = false;
        } else {
          res[i][j] = true;
        }
      }
    }
    return res;
  }
}
