package bean;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import constant.Constants;
import jakarta.annotation.PreDestroy;
import jna.JNALib;
import jna.JnaCGamesStructure;
import lombok.Getter;
import game.RaceGame;
import lombok.Setter;
import main.MainWrapperCGames;
import model.State;
import model.WebGameInfo;
import model.WebGameState;
import model.WebGamesList;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class ServerBean {

  private WebGamesList gamesList;
  private WebGameState gameState = null;
  private RaceGame raceGame = null;
  private MainWrapperCGames snakeGame = null;
  private MainWrapperCGames tetrisGame = null;
  private Integer idGame = 0;
  private Integer conditionOfCGame = 0;

  public ServerBean() {
    gamesList = new WebGamesList();
    WebGameInfo tetris = new WebGameInfo(1, "Tetris");
    WebGameInfo snake = new WebGameInfo(2, "Snake");
    WebGameInfo race = new WebGameInfo(3, "Race");
    var list = gamesList.getWebGameInfoList();
    list.add(tetris);
    list.add(snake);
    list.add(race);
  }

  @PreDestroy
  public void free() {
    if (raceGame != null) {
      JNALib.INSTANCE.freeStructure(raceGame.getRaceService().getJnaState());
    }
    if (tetrisGame != null) {
      tetrisGame.userInput(Constants.TERMINATE, false);
    }
    if (snakeGame != null) {
      snakeGame.deleteSnake();
    }
  }

  public void exChangeDate() {
    JnaCGamesStructure jnaState;
    if (idGame == 1) {
      jnaState = tetrisGame.get_info_extern();
    } else {
      jnaState = snakeGame.get_info_extern();
    }
    exChangeTwoDimensionalArray(gameState.getField(), Constants.HEIGHT, Constants.WIDTH,
        jnaState.field);
    exChangeTwoDimensionalArray(gameState.getNext(), Constants.WIDTHFORNEXT,
        Constants.WIDTHFORNEXT, jnaState.next);
    gameState.setScore(jnaState.score);
    gameState.setHigh_score(jnaState.high_score);
    gameState.setLvl(jnaState.level);
    gameState.setSpeed(jnaState.speed);
    if (jnaState.pause == 1) {
      gameState.setPause(true);
      conditionOfCGame = Constants.PAUSE;
    } else if (jnaState.pause == 0) {
      gameState.setPause(false);
      conditionOfCGame = Constants.GAME;
    } else {
      conditionOfCGame = jnaState.pause;
    }
  }

  public void exChangeDate(State state) {
    gameState.setField(state.field());
    gameState.setNext(state.next());
    gameState.setScore(state.score());
    gameState.setHigh_score(state.highScore());
    gameState.setLvl(state.level());
    gameState.setSpeed(state.speed());
    gameState.setPause(state.pause());
  }

  public void exChangeTwoDimensionalArray(boolean[][] array, int rows, int cols,
      Pointer jnaState) {
    if (jnaState != null) {
      int[] arrayData = new int[cols];
      for (int i = 0; i < rows; i++) {
        Pointer pt = jnaState.getPointer(i * Native.POINTER_SIZE);
        pt.read(0, arrayData, 0, cols);
        for (int j = 0; j < cols; j++) {
          if (arrayData[j] != 0) {
            array[i][j] = true;
          } else {
            array[i][j] = false;
          }
        }
      }
    }
  }

  public void startRace() {
    idGame = 3;
    if (gameState == null) {
      gameState = new WebGameState();
    }
    if (raceGame == null) {
      raceGame = new RaceGame();
      raceGame.start();
    } else {
      raceGame.restart();
    }
  }

  public void startTetris() {
    idGame = 1;
    if (gameState == null) {
      gameState = new WebGameState();
    }
    if (tetrisGame == null) {
      tetrisGame = new MainWrapperCGames(false);
    } else {
      tetrisGame.userInput(Constants.START, false);
    }
  }

  public void startSnake() {
    idGame = 2;
    if (gameState == null) {
      gameState = new WebGameState();
    }
    if (snakeGame == null) {
      snakeGame = new MainWrapperCGames(true);
    } else {
      snakeGame.userInput(Constants.START, false);
    }
  }


}
