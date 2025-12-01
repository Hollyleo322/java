package main.demo.controller;


import main.demo.agent.Agent;
import main.demo.model.AutoGen;
import main.demo.model.Cave;
import main.demo.model.Point;
import java.io.File;
import java.security.InvalidParameterException;
import java.util.Scanner;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import main.demo.alg.Decision;
import main.demo.alg.EllerAlgorithm;
import main.demo.model.Field;
import main.demo.painter.FxPainter;

public class Controller {

  @FXML
  private TextField row;

  @FXML
  private TextField col;

  @FXML
  private Button load;

  @FXML
  private Canvas maze;

  @FXML
  private TextField startedx;

  @FXML
  private TextField startedy;

  @FXML
  private TextField finishedx;

  @FXML
  private TextField finishedy;


  @FXML
  private Canvas cave;

  @FXML
  private TextField fieldBirth;

  @FXML
  private TextField fieldDeath;

  @FXML
  private TextField fieldChance;

  @FXML
  private TextField fieldInterval;

  @FXML
  private TextField startedxAgent;

  @FXML
  private TextField startedyAgent;
  @FXML
  private Canvas mazeAI;

  private Field currentField = null;

  private Cave currentCave = null;

  @FXML
  private void onClickedLoad() {
    File chosenFile = getFile("Load maze file");
    if (chosenFile != null) {
      if (chosenFile.canRead()) {
        try {
          currentField = scanMazeFile(chosenFile);
          FxPainter fxPainter = new FxPainter(maze.getGraphicsContext2D(), maze);
          fxPainter.drawField(currentField.getRows(), currentField.getCols(),
              currentField.getVertical(),
              currentField.getHorizontal(), true);
        } catch (Exception e) {
          showMessageOfError(e, "Error of reading file");
        }
      }
    }
  }

  private File getFile(String title) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle(title);
    fileChooser.getExtensionFilters().add(new ExtensionFilter("Txt files", "*.txt"));
    return fileChooser.showOpenDialog(load.getScene().getWindow());
  }

  private void showMessageOfError(Exception e, String title) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setContentText(e.getClass() + " " + e.getMessage());
    alert.show();
  }

  @FXML
  private void onClickedLoadMazeForAgent() {
    File chosenFile = getFile("Load maze file with ended point");
    if (chosenFile != null) {
      if (chosenFile.canRead()) {
        try {
          currentField = scanMazeFile(chosenFile);
          int startedX = Integer.parseInt(startedxAgent.getText());
          int startedY = Integer.parseInt(startedyAgent.getText());
          if (startedX < 0 || startedX >= currentField.getCols() || startedY < 0
              || startedY >= currentField.getRows()) {
            throw new InvalidParameterException("Not valid data in the field");
          }
          try (Scanner scanner = new Scanner(chosenFile)) {
            String endStr = "";
            while (scanner.hasNextLine()) {
              endStr = scanner.nextLine();
            }
            String[] coords = endStr.split(" ");
            if (coords.length != 2) {
              throw new InvalidParameterException("There is no ended point");
            }
            int endedX = Integer.parseInt(coords[0]);
            int endedY = Integer.parseInt(coords[1]);
            if (endedY < 0 || endedX < 0 || endedY >= currentField.getRows() || endedX >= currentField.getCols()) {
              throw new InvalidParameterException("Invalid ended point");
            }
            Point end = new Point(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), null);
            Point start = new Point(Integer.parseInt(startedxAgent.getText()),
                Integer.parseInt(startedyAgent.getText()), null);
            if (start.x == end.x && start.y == end.y) {
              throw new InvalidParameterException("Started and ended points must be not equals each other");
            }
            Agent agent = new Agent(currentField.getRows(), currentField.getCols(), 0.1, 0.99, 0.2,
                start, end, currentField);
            agent.train();
            FxPainter fxPainter = new FxPainter(mazeAI.getGraphicsContext2D(), mazeAI);
            fxPainter.drawField(currentField.getRows(), currentField.getCols(),
                currentField.getVertical(), currentField.getHorizontal(), true);
            fxPainter.drawDecision(agent.getPath(), currentField);
          }
        } catch (Exception e) {
          showMessageOfError(e, "Error of reading file");
        }
      }
    }
  }

  @FXML
  private void onClickedAutoGen() {
    if (currentCave != null) {
      try {
        int interval = Integer.parseInt(fieldInterval.getText());
        if (interval < 0) {
          throw new InvalidParameterException("Interval must be greater 0");
        }
        Thread thread = new Thread(
            new AutoGen(currentCave, null, new FxPainter(cave.getGraphicsContext2D(), cave),
                interval));
        thread.start();
      } catch (Exception e) {
        showMessageOfError(e, "Not valid data in the field");
      }
    }
  }

  private Field scanMazeFile(File chosenFile) throws Exception {
    int rows, cols;
    int[][] vertical, horizontal;
    try (Scanner scanner = new Scanner(chosenFile)) {
      rows = scanner.nextInt();
      cols = scanner.nextInt();
      scanner.nextLine();
      if (rows < 0 || rows > 50 || cols < 0 || cols > 50) {
        throw new InvalidParameterException(
            "rows or cols must be greater than 0 and lesser than 51");
      } else {
        vertical = initArray(rows, cols, scanner);
        scanner.nextLine();
        horizontal = initArray(rows, cols, scanner);
      }
    }
    return new Field(vertical, horizontal, rows, cols);
  }

  @FXML
  private void onClickedLoadCave() {
    File chosenFile = getFile("Load cave file");
    if (chosenFile != null) {
      if (chosenFile.canRead()) {
        try (Scanner scanner = new Scanner(chosenFile)) {
          int rows = scanner.nextInt();
          int cols = scanner.nextInt();
          scanner.nextLine();
          if (rows < 0 || rows > 50 || cols < 0 || cols > 50) {
            throw new InvalidParameterException(
                "rows or cols must be greater than 0 and lesser than 51");
          } else {
            int[][] fieldCave = initArray(rows, cols, scanner);
            int limitBirth = Integer.parseInt(fieldBirth.getText());
            int limitDeath = Integer.parseInt(fieldDeath.getText());
            int chancePercent = Integer.parseInt(fieldChance.getText());
            if (chancePercent < 0 || chancePercent > 100 || limitBirth < 0 || limitBirth > 7
                || limitDeath < 0 || limitDeath > 7) {
              throw new InvalidParameterException("not valid data in the field");
            }
            currentCave = new Cave(fieldCave, rows, cols, limitBirth, limitDeath);
            currentCave.initWithChance(chancePercent);
            FxPainter fxPainter = new FxPainter(cave.getGraphicsContext2D(), cave);
            fxPainter.drawCave(currentCave);
          }
        } catch (Exception e) {
          showMessageOfError(e, "Error in scanning of file");
        }
      }
    }
  }

  @FXML
  private void onClickedGen() {
    try {
      int rows = Integer.parseInt(row.getText());
      int cols = Integer.parseInt(col.getText());
      if (rows < 0 || rows > 50 || cols < 0 || cols > 50) {
        throw new InvalidParameterException(
            "rows or cols must be greater than 0 and lesser than 51");
      }
      currentField = EllerAlgorithm.getField(rows, cols);
      FxPainter fxPainter = new FxPainter(maze.getGraphicsContext2D(), maze);
      fxPainter.drawField(rows, cols, currentField.getVertical(), currentField.getHorizontal(),
          false);
    } catch (Exception e) {
      showMessageOfError(e, e.getMessage());
    }
  }

  public static int[][] initArray(int rows, int cols, Scanner scanner) {
    int[][] result = new int[rows][cols];
    for (int i = 0; i < rows; i++) {
      String[] numbers = scanner.nextLine().split(" ");
      for (int j = 0; j < cols; j++) {
        result[i][j] = Integer.parseInt(numbers[j]);
      }
    }
    return result;
  }

  @FXML
  private void onClickedNextGen() {
    if (currentCave != null) {
      currentCave.generateNextGen();
      FxPainter fxPainter = new FxPainter(cave.getGraphicsContext2D(), cave);
      fxPainter.drawCave(currentCave);
    }
  }

  @FXML
  private void onClickedDecision() {
    if (currentField != null) {
      FxPainter fxPainter = new FxPainter(maze.getGraphicsContext2D(), maze);
      fxPainter.drawField(currentField.getRows(), currentField.getCols(),
          currentField.getVertical(),
          currentField.getHorizontal(), false);
    }
    try {
      int startedX = Integer.parseInt(startedx.getText());
      int startedY = Integer.parseInt(startedy.getText());
      int finishedX = Integer.parseInt(finishedx.getText());
      int finishedY = Integer.parseInt(finishedy.getText());
      if (startedX < 0 || startedX >= currentField.getCols() || startedY < 0
          || startedY >= currentField.getRows()) {
        throw new InvalidParameterException("Not valid data in the field");
      }
      if (finishedX < 0 || finishedX >= currentField.getCols() || finishedY < 0
          || finishedY >= currentField.getRows()) {
        throw new InvalidParameterException("Not valid data in the field");
      }
      if (startedX == finishedX && startedY == finishedY) {
        throw new InvalidParameterException("Started can't be equals to finished");
      }
      Decision dec = new Decision();
      dec.findDecision(currentField, new Point(startedX, startedY, null),
          new Point(finishedX, finishedY, null));
      FxPainter fxPainter = new FxPainter(maze.getGraphicsContext2D(), maze);
      fxPainter.drawDecision(dec.getPath(), currentField);
    } catch (Exception e) {
      showMessageOfError(e, "Not valid data in the field");
    }
  }

}