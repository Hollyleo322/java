package main.demo.painter;

import java.security.InvalidParameterException;
import java.util.List;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import lombok.AllArgsConstructor;
import main.demo.model.Cave;
import main.demo.model.Field;
import main.demo.model.Point;

@AllArgsConstructor
public class FxPainter {

  private GraphicsContext gc;
  private Canvas canvas;

  public void drawField(int rows, int cols, int[][] vertical, int[][] horizontal,
      boolean fromFile) {
    boolean isLeft = checkHorizontal(rows, cols, vertical, fromFile);
    boolean isTop = checkVertical(rows, cols, horizontal, fromFile);
    gc.clearRect(0, 0, 499, 499);
    gc.setStroke(Paint.valueOf("black"));
    gc.setLineWidth(2);
    gc.strokeRect(0, 0, 499, 499);
    drawVertical(rows, cols, vertical, isLeft);
    drawHorizontal(rows, cols, horizontal, isTop);
  }

  public void drawDecision(List<Point> path, Field field) {
    int sizeRow = (int) (canvas.getHeight() / field.getRows()), sizeCol = (int) (canvas.getWidth()
        / field.getCols());
    int adderCol = sizeCol / 2;
    int adderRow = sizeRow / 2;
    boolean verticalPrevious = false;
    gc.setStroke(Paint.valueOf("red"));
    gc.setLineWidth(2);
    for (Point pt : path) {
      if (path.indexOf(pt) == 0) {
        verticalPrevious = drawFinishCell(pt, sizeCol, sizeRow, adderCol, adderRow);
      } else {
        if (needVertical(pt)) {
          if (!verticalPrevious) {
            drawHalfHorizontalToChildren(pt.x, pt.y, sizeCol, sizeRow, adderCol, adderRow, pt,
                path);
            drawHalfVerticalToParent(pt.x, pt.y, sizeCol, sizeRow, adderCol, adderRow, pt);
          } else {
            drawVerticalDecision(pt.x, pt.y, sizeCol, sizeRow, adderCol);
          }
          verticalPrevious = true;
        } else {
          if (verticalPrevious) {
            drawHalfHorizontalToParent(pt.x, pt.y, sizeCol, sizeRow, adderCol, adderRow, pt);
            drawHalfVerticalToChildren(pt.x, pt.y, sizeCol, sizeRow, adderCol, adderRow, pt, path);
          } else {
            drawHorizontalDecision(pt.x, pt.y, sizeCol, sizeRow, adderRow);
          }
          verticalPrevious = false;
        }
      }
      Point last = path.get(path.size() - 1);
      Point start = last.getParent();
      if (needVertical(last)) {
        drawHalfVerticalToChildren(start.x, start.y, sizeCol, sizeRow, adderCol, adderRow, start,
            path);
      } else {
        drawHalfHorizontalToChildren(start.x, start.y, sizeCol, sizeRow, adderCol, adderRow, start,
            path);
      }
    }
  }

  private boolean drawFinishCell(Point pt, int sizeCol, int sizeRow, int adderCol, int adderRow) {
    boolean vertical = false;
    if (needVertical(pt)) {
      vertical = true;
      drawHalfVerticalToParent(pt.x, pt.y, sizeCol, sizeRow, adderCol, adderRow, pt);
    } else {
      drawHalfHorizontalToParent(pt.x, pt.y, sizeCol, sizeRow, adderCol, adderRow, pt);
    }
    return vertical;
  }

  private void drawHalfHorizontalToChildren(int x, int y, int sizeCol, int sizeRow, int adderCol,
      int adderRow, Point current, List<Point> path) {
    if (childrenFromRight(current, path)) {
      gc.strokeLine(x * sizeCol + adderCol, y * sizeRow + adderRow, (x + 1) * sizeCol,
          y * sizeRow + adderRow);
    } else {
      gc.strokeLine(x * sizeCol, y * sizeRow + adderRow, x * sizeCol + adderCol,
          y * sizeRow + adderRow);
    }
  }

  private boolean childrenFromRight(Point current, List<Point> path) {
    boolean result = false;
    for (Point it : path) {
      if (current.x == it.getParent().x && current.y == it.getParent().y) {
        if (it.x > current.x) {
          result = true;
        }
        break;
      }
    }
    return result;
  }

  private void drawHalfVerticalToChildren(int x, int y, int sizeCol, int sizeRow, int adderCol,
      int adderRow, Point current, List<Point> path) {
    if (childrenAbove(current, path)) {
      gc.strokeLine(x * sizeCol + adderCol, y * sizeRow, x * sizeCol + adderCol,
          y * sizeRow + adderRow);
    } else {
      gc.strokeLine(x * sizeCol + adderCol, y * sizeRow + adderRow, x * sizeCol + adderCol,
          (y + 1) * sizeRow);
    }
  }

  private boolean childrenAbove(Point current, List<Point> path) {
    boolean result = false;
    for (Point it : path) {
      if (current.x == it.getParent().x && current.y == it.getParent().y) {
        if (it.y < current.y) {
          result = true;
        }
        break;
      }
    }
    return result;
  }

  private void drawHalfHorizontalToParent(int x, int y, int sizeCol, int sizeRow, int adderCol,
      int adderRow, Point current) {
    if (!parentFromRight(current)) {
      gc.strokeLine(x * sizeCol, y * sizeRow + adderRow, x * sizeCol + adderCol,
          y * sizeRow + adderRow);
    } else {
      gc.strokeLine(x * sizeCol + adderCol, y * sizeRow + adderRow, (x + 1) * sizeCol,
          y * sizeRow + adderRow);
    }
  }

  private boolean parentFromRight(Point current) {
    return current.x < current.getParent().x;
  }

  private void drawHalfVerticalToParent(int x, int y, int sizeCol, int sizeRow, int adderCol,
      int adderRow, Point current) {
    if (!parentFromUp(current)) {
      gc.strokeLine(x * sizeCol + adderCol, y * sizeRow + adderRow, x * sizeCol + adderCol,
          (y + 1) * sizeRow);
    } else {
      gc.strokeLine(x * sizeCol + adderCol, y * sizeRow, x * sizeCol + adderCol,
          y * sizeRow + adderRow);
    }
  }

  private boolean parentFromUp(Point current) {
    return current.y > current.getParent().y;
  }

  private boolean needVertical(Point pt) {
    return pt.getParent().y != pt.y;
  }

  private void drawVerticalDecision(int x, int y, int sizeCol, int sizeRow, int adder) {
    gc.strokeLine(x * sizeCol + adder, y * sizeRow, x * sizeCol + adder, (y + 1) * sizeRow);
  }

  private void drawHorizontalDecision(int x, int y, int sizeCol, int sizeRow, int adder) {
    gc.strokeLine(x * sizeCol, y * sizeRow + adder, (x + 1) * sizeCol, y * sizeRow + adder);
  }

  private void drawVertical(int rows, int cols, int[][] vertical, boolean isLeft) {
    int ct = 0, colsI = cols, sizeRow = (int) (canvas.getHeight() / rows), sizeCol = (int) (
        canvas.getWidth() / cols);
    if (isLeft) {
      ct = 1;
    } else {
      colsI = cols - 1;
    }
    for (int i = 0; i < rows; i++) {
      for (int j = ct; j < colsI; j++) {
        if (vertical[i][j] == 1 && isLeft) {
          gc.strokeLine(j * sizeCol, i * sizeRow, j * sizeCol, (i + 1) * sizeRow);
        } else if (vertical[i][j] == 1 && !isLeft) {
          gc.strokeLine((j + 1) * sizeCol, i * sizeRow, (j + 1) * sizeCol, (i + 1) * sizeRow);
        }
      }
    }
  }

  private void drawHorizontal(int rows, int cols, int[][] horizontal, boolean isTop) {
    int ct = 0, rowsI = rows, sizeCol = (int) (canvas.getWidth() / cols), sizeRow = (int) (
        canvas.getHeight() / rows);
    if (isTop) {
      ct = 1;
    } else {
      rowsI = rows - 1;
    }
    for (int i = ct; i < rowsI; i++) {
      for (int j = 0; j < cols; j++) {
        if (horizontal[i][j] == 1 && isTop) {
          gc.strokeLine(j * sizeCol, i * sizeRow, (j + 1) * sizeCol, i * sizeRow);
        } else if (horizontal[i][j] == 1 && !isTop) {
          gc.strokeLine(j * sizeCol, (i + 1) * sizeRow, (j + 1) * sizeCol, (i + 1) * sizeRow);
        }
      }
    }
  }

  private boolean checkVertical(int rows, int cols, int[][] array, boolean fromFile) {
    if (fromFile) {
      checkBothVerticalLines(rows, cols, array);
    }
    boolean check = true;
    boolean isTop = true;
    for (int i = 0; i < cols; i++) {
      if (array[0][i] != 1) {
        check = false;
        isTop = false;
        break;
      }
    }
    if (!check) {
      check = true;
      for (int j = 0; j < cols; j++) {
        if (array[rows - 1][j] != 1) {
          check = false;
          break;
        }
      }
    }
    if (!check) {
      throw new InvalidParameterException("Invalid data in array");
    }
    return isTop;
  }

  private boolean checkHorizontal(int rows, int cols, int[][] array, boolean fromFile) {
    if (fromFile) {
      checkBothHorizontalLines(rows, cols, array);
    }
    boolean check = true;
    boolean isLeft = true;
    for (int i = 0; i < rows; i++) {
      if (array[i][0] != 1) {
        check = false;
        isLeft = false;
        break;
      }
    }
    if (!check) {
      check = true;
      for (int j = 0; j < rows; j++) {
        if (array[j][cols - 1] != 1) {
          check = false;
          break;
        }
      }
    }
    if (!check) {
      throw new InvalidParameterException("Invalid data in array");
    }
    return isLeft;
  }

  private void checkBothVerticalLines(int rows, int cols, int[][] array) {
    boolean same = true;
    for (int i = 0; i < rows; i++) {
      if (array[0][i] != 1 || array[cols - 1][i] != 1) {
        same = false;
        break;
      }
    }
    if (same) {
      throw new InvalidParameterException("Invalid data in array");
    }
  }

  private void checkBothHorizontalLines(int rows, int cols, int[][] array) {
    boolean same = true;
    for (int i = 0; i < cols; i++) {
      if (array[i][0] != 1 || array[i][rows - 1] != 1) {
        same = false;
        break;
      }
    }
    if (same) {
      throw new InvalidParameterException("Invalid data in array");
    }
  }

  public void drawCave(Cave currentCave) {
    gc.clearRect(0, 0, 499, 499);
    int sizeCol = (int) canvas.getWidth() / currentCave.getCols(), sizeRow =
        (int) canvas.getHeight() / currentCave.getRows();
    gc.setLineWidth(2);
    gc.strokeRect(0, 0, canvas.getWidth() - 1, canvas.getHeight() - 1);
    gc.setLineWidth(4);
    for (int i = 0; i < currentCave.getRows(); i++) {
      for (int j = 0; j < currentCave.getCols(); j++) {
        if (currentCave.getField()[i][j] == 1) {
          gc.fillRect(j * sizeCol, i * sizeRow, sizeCol, sizeRow);
        }
      }
    }
  }
}
