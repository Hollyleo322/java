package run;

import java.io.IOException;
import wrapper.jna.JNALibLoader;
import wrapper.jna.JnaCLibrary;
import wrapper.main.MainWrapper;

public class Cli {

  public static void main(String[] args) {
    JNALibLoader.initInstance(false);
    MainWrapper mainWrapper = new MainWrapper();
    mainWrapper.startRace();
    JnaCLibrary lib = JNALibLoader.getInstance();
    lib.startCli();
    lib.freeStructure(mainWrapper.getJnaStructure());
  }
}