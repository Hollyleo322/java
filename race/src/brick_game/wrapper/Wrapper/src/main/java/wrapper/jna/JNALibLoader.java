package wrapper.jna;

import com.sun.jna.Native;
import lombok.Getter;


public class JNALibLoader {

  @Getter
  private static JnaCLibrary instance;

  public static void initInstance(boolean plus) {
    if (plus) {
      instance = Native.load("s21_race_desktop", JnaCLibrary.class);
    } else {
      instance = Native.load("race_cli", JnaCLibrary.class);
    }
  }
}
