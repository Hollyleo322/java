package jna;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface JNALib extends Library {

  JNALib INSTANCE = Native.load("MyLibC", JNALib.class);

  void output(JNAState state);

  JNAState getStatePtr();

  void freeStructure(JNAState state);

  void initUserAction(JNACallBackUserAction callBack, JNACallBackUpdateState updateState);

  void userInput(int action, boolean hold);

  JNAState.ByValue updateCurrentState();
}
