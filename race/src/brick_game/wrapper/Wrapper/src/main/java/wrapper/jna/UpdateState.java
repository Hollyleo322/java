package wrapper.jna;

import com.sun.jna.Callback;

public interface UpdateState extends Callback {

  JnaStructureOfC invoke();
}
