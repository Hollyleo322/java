package jna;

import game.RaceGame;


public class UserAction implements JNACallBackUserAction {

  private RaceGame game;

  public UserAction(RaceGame game) {
    this.game = game;
  }

  @Override
  public void invoke(int action) {
    game.doAction(action);
  }
}
