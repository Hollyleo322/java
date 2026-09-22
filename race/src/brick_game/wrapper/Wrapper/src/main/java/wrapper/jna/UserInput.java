package wrapper.jna;

import com.sun.jna.Callback;

public interface UserInput extends Callback {

  void action(int action, boolean hold);
}
