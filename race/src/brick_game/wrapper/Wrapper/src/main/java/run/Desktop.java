package run;

import wrapper.jna.JNALibLoader;
import wrapper.jna.JnaCLibrary;
import wrapper.main.MainWrapper;

public class Desktop {

  public static void main(String[] args) {
    JNALibLoader.initInstance(true);
    MainWrapper mainWrapper = new MainWrapper();
    mainWrapper.startRace();
    JnaCLibrary lib = JNALibLoader.getInstance();
    lib.startDesktop();
    lib.freeStructure(mainWrapper.getJnaStructure());
  }
}
