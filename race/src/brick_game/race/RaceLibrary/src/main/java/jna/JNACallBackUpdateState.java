package jna;

import com.sun.jna.Callback;

public interface JNACallBackUpdateState extends Callback {

  JNAState invoke();
}
