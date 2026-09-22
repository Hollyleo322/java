package graph;

import exception.GraphIsNull;
import exception.MatrixIsNotLoadedException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Graph {


  private int[][] matrix = null;
  private boolean isWeighed = false;
  private boolean isDirected = false;

  public void loadGraphFromFile(String filename) throws FileNotFoundException {
    try (Scanner scanner = new Scanner(new File(filename))) {
      int size = scanner.nextInt();
      if (size > 1) {
        matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
          for (int j = 0; j < size; j++) {
            matrix[i][j] = scanner.nextInt();
          }
        }
        checkWeight();
        checkDirected();
        checkNullable();
      } else {
        System.out.println("Incorrect size value");
      }
    }
  }

  public void exportGraphToDot(String filename) {
    if (matrix != null) {
      String dir = "dot";
      File makeDir = new File(dir);
      makeDir.mkdir();
      File file = new File(dir + "/" + filename + ".dot");
      try {
        file.createNewFile();
      } catch (IOException e) {
        System.out.println("Can't create file");
      }
      try (PrintWriter printWriter = new PrintWriter(file)) {
        String between = "--";
        String graph = "graph";
        String graphName = "s21_graph";
        if (isDirected) {
          between = "->";
          graph = "di" + graph;
        }
        printWriter.println(graph + " " + graphName + " {");
        for (int i = 0; i < matrix.length; i++) {
          printWriter.printf("\t%d;\n", i + 1);
        }
        for (int i = 0; i < matrix.length; i++) {
          for (int j = 0; j < matrix.length; j++) {
            if (matrix[i][j] != 0) {
              if (!isDirected && j < i) {
                continue;
              }
              printWriter.printf("\t%d " + between + " %d;\n", i + 1, j + 1);
            }
          }
        }
        printWriter.println("}");
      } catch (FileNotFoundException e) {
        System.out.println("Can't find file");
      }
    } else {
      throw new MatrixIsNotLoadedException("Matrix isn't loaded");
    }
  }

  private void checkNullable() {
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix.length; j++) {
        if (matrix[i][j] != 0) {
          return;
        }
      }
    }
    throw new GraphIsNull("There is no edge in the graph");
  }

  private void checkWeight() {
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix.length; j++) {
        if (matrix[i][j] < 0) {
          throw new IllegalArgumentException("Weight can't be lesser than zero");
        } else if (matrix[i][j] > 1) {
          isWeighed = true;
        }
      }
    }
  }

  private void checkDirected() {
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix.length; j++) {
        if (i == j) {
          continue;
        } else if (matrix[i][j] != matrix[j][i]) {
          isDirected = true;
          return;
        }
      }
    }
  }
}
