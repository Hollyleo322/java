package main;

import jna.JnaCGamesLibLoader;
import jna.JnaCGamesLibrary;
import jna.JnaCGamesStructure;

public class MainWrapperCGames {

  private JnaCGamesLibrary library;

  public MainWrapperCGames(boolean plus) {
    JnaCGamesLibLoader jnaCGamesLibLoader;
    if (plus) {
      jnaCGamesLibLoader = new JnaCGamesLibLoader(true);
    } else {
      jnaCGamesLibLoader = new JnaCGamesLibLoader(false);
    }
    library = jnaCGamesLibLoader.getLibrary();
    if (plus) {
      library.runSnake();
    } else {
      library.runTetris();
    }
  }

  public void userInput(int action, boolean hold) {
    library.userInput(action, hold);
  }

  public JnaCGamesStructure.ByValue updateCurrentState() {
    return library.updateCurrentState();
  }

  public JnaCGamesStructure.ByValue get_info_extern() {
    return library.get_info_extern();
  }

  public void deleteSnake() {
    library.deleteSnake();
  }
}
