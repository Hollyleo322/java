package jna;


import com.sun.jna.Native;
import lombok.Getter;

@Getter
public class JnaCGamesLibLoader {

  private final JnaCGamesLibrary library;

  public JnaCGamesLibLoader(boolean plus) {
    if (plus) {
      library = Native.load("s21_snake", JnaCGamesLibrary.class);
    } else {
      library = Native.load("s21_tetris", JnaCGamesLibrary.class);
    }
  }
}
