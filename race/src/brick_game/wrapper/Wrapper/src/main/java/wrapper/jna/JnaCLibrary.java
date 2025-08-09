package wrapper.jna;

import com.sun.jna.Library;
import com.sun.jna.Native;
import wrapper.jna.JnaStructureOfC.ByValue;

public interface JnaCLibrary extends Library {

  void initUserAction(UserInputRequest userInput, UpdateStateRequest updateState,
      GetStateRequest getStateRequest);

  void startDesktop();

  ByValue getInfo();

  ByValue updateCurrentState();

  JnaStructureOfC getStatePtr();

  void freeStructure(JnaStructureOfC state);

  void startCli();
}
