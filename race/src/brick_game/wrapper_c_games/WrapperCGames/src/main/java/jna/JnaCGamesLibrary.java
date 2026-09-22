package jna;

import com.sun.jna.Library;

public interface JnaCGamesLibrary extends Library {

  JnaCGamesStructure.ByValue updateCurrentState();

  JnaCGamesStructure.ByValue get_info_extern();

  void userInput(int action, boolean hold);

  void runTetris();

  void runSnake();

  void deleteSnake();
}
