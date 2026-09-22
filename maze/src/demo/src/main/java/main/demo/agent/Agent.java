package main.demo.agent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.Getter;
import main.demo.constants.Constants;
import main.demo.model.Field;
import main.demo.model.Point;

public class Agent {

  private final double alpha;
  private final double gamma;
  private final double epsilon;
  private final int rows;
  private final int cols;
  @Getter
  private List<Point> path;
  private final double[][] qTable;
  private final Point start;
  private final Point end;
  private final Field currentField;
  Random random;

  public Agent(int rows, int cols, double alpha, double gamma, double epsilon, Point start,
      Point end, Field currentField) {
    this.rows = rows;
    this.cols = cols;
    this.alpha = alpha;
    this.gamma = gamma;
    this.epsilon = epsilon;
    path = new ArrayList<>();
    qTable = new double[rows * cols][Constants.LENGTHMOVES];
    random = new Random();
    this.currentField = currentField;
    this.start = start;
    this.end = end;
  }

  public int getAction(int state) {
    int result;
    if (random.nextDouble(1.1) > epsilon) {
      do {
        result = random.nextInt(4);
      }
      while (!availableMove(state, result));
    } else {
      result = takeBestAction(state);
    }
    return result;
  }

  public int takeBestAction(int state) {
    double max = -Double.MAX_VALUE;
    int result = -1;
    for (int j = 0; j < Constants.LENGTHMOVES; j++) {
      if (qTable[state][j] > max && availableMove(state, j)) {
        max = qTable[state][j];
        result = j;
      }
    }
    return result;
  }

  public boolean availableMove(int state, int move) {
    boolean result = false;
    int i = state / cols;
    int j = state % cols;
    switch (move) {
      case Constants.UP:
        if (isInside(i - 1, j)) {
          result = isPossible(i, j, move);
        }
        break;
      case Constants.DOWN:
        if (isInside(i + 1, j)) {
          result = isPossible(i, j, move);
        }
        break;
      case Constants.LEFT:
        if (isInside(i, j - 1)) {
          result = isPossible(i, j, move);
        }
        break;
      case Constants.RIGHT:
        if (isInside(i, j + 1)) {
          result = isPossible(i, j, move);
        }
        break;
    }
    return result;
  }

  public boolean isInside(int i, int j) {
    return i >= 0 && i < rows && j >= 0 && j < cols;
  }

  public boolean isPossible(int i, int j, int move) {
    boolean result = true;
    switch (move) {
      case Constants.UP:
        if (currentField.getHorizontal()[i - 1][j] == 1) {
          result = false;
        }
        break;
      case Constants.DOWN:
        if (currentField.getHorizontal()[i][j] == 1) {
          result = false;
        }
        break;
      case Constants.LEFT:
        if (currentField.getVertical()[i][j - 1] == 1) {
          result = false;
        }
        break;
      case Constants.RIGHT:
        if (currentField.getVertical()[i][j] == 1) {
          result = false;
        }
        break;
    }
    return result;
  }

  public int getReward(int state) {
    int result = 0;
    int i = state / cols;
    int j = state % cols;
    if (i == end.y && j == end.x) {
      result = 100;
    } else {
      result = -1;
    }
    return result;
  }

  public double getQValue(int state, int action) {
    return qTable[state][action];
  }

  public int getNextState(int state, int action) {
    int i = state / cols;
    int j = state % cols;
    switch (action) {
      case Constants.UP -> i -= 1;
      case Constants.DOWN -> i += 1;
      case Constants.LEFT -> j -= 1;
      case Constants.RIGHT -> j += 1;
    }
    return i * cols + j;
  }

  public void updateQValue(int state, int action) {
    int nextState = getNextState(state, action);
    double currentQValue = getQValue(state, action);
    qTable[state][action] = currentQValue + alpha * (
        getReward(nextState) + gamma * getQValue(nextState, takeBestAction(nextState))
            - currentQValue);
  }

  public void train() {
    int episodes = 1000;
    int state = start.y * cols + start.x;
    for (int i = 0; i < episodes; i++) {
      do {
        int action = getAction(state);
        updateQValue(state, action);
        state = getNextState(state, action);
      } while (!inEnd(state));
    }
    initBetterPath();
  }

  public boolean inEnd(int state) {
    boolean result = false;
    int i = state / cols;
    int j = state % cols;
    if (end.x == j && end.y == i) {
      result = true;
    }
    return result;
  }

  private void initBetterPath() {
    int startState = start.y * cols + start.x;
    Point parent = new Point(start.x, start.y, null);
    int state = getNextState(startState, takeBestAction(startState));
    do {
      Point current = new Point(state % cols, state / cols, parent);
      path.add(current);
      parent = current;
      int action = takeBestAction(state);
      state = getNextState(state, action);
    } while (!inEnd(state));
    path.add(new Point(state % cols, state / cols, parent));
    reversePath();
  }

  private void reversePath() {
    List<Point> reversedList = new ArrayList<>();
    for (int i = path.size() - 1; i >= 0; i--) {
      reversedList.add(path.get(i));
    }
    path = reversedList;
  }
}
