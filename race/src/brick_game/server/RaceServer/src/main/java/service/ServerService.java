package service;

import bean.ServerBean;
import constant.Constants;
import jna.JNALib;
import lombok.AllArgsConstructor;
import model.WebGameInfo;
import java.util.List;

import model.WebGameState;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ServerService {

  private ServerBean serverBean;

  public List<WebGameInfo> getAvailableGames() {
    return serverBean.getGamesList().getWebGameInfoList();
  }

  public WebGameState getGameState() {
    if (serverBean.getGameState() != null) {
      if (serverBean.getIdGame() == 1 || serverBean.getIdGame() == 2) {
        serverBean.exChangeDate();
      } else if (serverBean.getIdGame() == 3) {
        serverBean.exChangeDate(serverBean.getRaceGame().getState());
      }
    }
    return serverBean.getGameState();
  }

  public void startGame(Integer gameId) {
    if (gameId == 1) {
      serverBean.startTetris();
    } else if (gameId == 2) {
      serverBean.startSnake();
    } else if (gameId == 3) {
      serverBean.startRace();
    }
  }

  public void doAction(Integer action_id, Boolean hold) {
    if (serverBean.getIdGame() == 1) {
      serverBean.getTetrisGame().userInput(action_id, hold);
    } else if (serverBean.getIdGame() == 2) {
      serverBean.getSnakeGame().userInput(action_id, hold);
    } else if (serverBean.getIdGame() == 3) {
      JNALib.INSTANCE.userInput(action_id, hold);
    }
    if (action_id == Constants.TERMINATE) {
      if (serverBean.getIdGame() == 1) {
        serverBean.setTetrisGame(null);
      }
      serverBean.setIdGame(0);
    }
    if (action_id == Constants.START && serverBean.getIdGame() == 1
        || serverBean.getIdGame() == 2) {
      serverBean.setConditionOfCGame(Constants.GAME);
    }
  }

  public void resetCondition() {
    serverBean.getRaceGame().setCondition(Constants.GAME);
  }

  public void resetRace() {
    serverBean.getRaceGame().restart();
    resetCondition();
  }

  public Integer getRaceCondition() {
    return serverBean.getRaceGame().getCondition();
  }

  public Integer getConditionCGame() {
    return serverBean.getConditionOfCGame();
  }

  public void setId(Integer id) {
    serverBean.setIdGame(id);
  }

  public void updateState() {
    if (serverBean.getIdGame() == 1) {
      serverBean.getTetrisGame().updateCurrentState();
    } else if (serverBean.getIdGame() == 2) {
      serverBean.getSnakeGame().updateCurrentState();
    } else if (serverBean.getIdGame() == 3) {
      JNALib.INSTANCE.updateCurrentState();
    }
  }

  public Integer getId() {
    return serverBean.getIdGame();
  }
}
