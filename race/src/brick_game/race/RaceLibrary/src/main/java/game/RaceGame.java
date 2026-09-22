package game;

import constant.Constants;
import jna.JNALib;
import jna.UpdateState;
import jna.UserAction;
import lombok.Getter;
import model.Player;
import model.Rival;
import model.State;
import service.RaceService;

@Getter
public class RaceGame {

  private Player player;
  private Rival rival;
  private RaceService raceService;
  private UserAction userAction;
  private UpdateState updateState;
  private Integer condition;

  public RaceGame() {
    player = new Player();
    rival = new Rival();
    raceService = new RaceService();
    userAction = new UserAction(this);
    updateState = new UpdateState(this);
    rival.createRival();
    condition = 0;
  }

  public void start() {
    JNALib.INSTANCE.initUserAction(userAction, updateState);
    raceService.setHighScore();
    restart();
    raceService.createBorders();
    raceService.settingData(player, rival);
  }

  public void restart() {
    condition = 0;
    raceService.restart();
    rival.getRivals().clear();
    rival.createRival();
  }

  public void updateState(boolean hold) {
    raceService.update(rival, hold);
    if (raceService.checkCollision(rival, player)) {
      condition = Constants.PRE_EXIT_SITUATION;
    }
    raceService.settingData(player, rival);
  }

  public State getState() {
    return raceService.getState();
  }

  public void doAction(int action) {
    if (action == Constants.RIGHT) {
      player.turnRight();
    } else if (action == Constants.LEFT) {
      player.turnLeft();
    } else if (action == Constants.START) {
      restart();
    } else if (action == Constants.TERMINATE) {
      raceService.setHighScoreInTheFile();
      condition = Constants.END_OF_GAME;
    } else if (action == Constants.UP) {
      updateState(true);
      raceService.setMaxSpeed();
    } else if (action == Constants.PAUSE) {
      if (condition == Constants.PAUSE) {
        condition = Constants.GAME;
      } else {
        condition = Constants.PAUSE;
      }
    }
    if (raceService.checkCollision(rival, player)) {
      condition = Constants.PRE_EXIT_SITUATION;
    }
    raceService.settingData(player, rival);
  }

  public void setCondition(int condition) {
    this.condition = condition;
  }

}
