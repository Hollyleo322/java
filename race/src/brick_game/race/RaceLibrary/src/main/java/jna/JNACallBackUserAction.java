package jna;

import com.sun.jna.Callback;

public interface JNACallBackUserAction extends Callback {

  void invoke(int action);
}
