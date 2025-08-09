package jna;

import game.RaceGame;

public class UpdateState implements JNACallBackUpdateState {

  private final RaceGame raceGame;

  public UpdateState(RaceGame raceGame) {
    this.raceGame = raceGame;
  }

  @Override
  public JNAState.ByValue invoke() {
    raceGame.updateState(false);
    return raceGame.getRaceService().getJnastateByValue();
  }

}
