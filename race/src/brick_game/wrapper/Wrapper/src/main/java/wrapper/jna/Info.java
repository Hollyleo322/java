package wrapper.jna;

import com.sun.jna.Callback;

public interface Info extends Callback {

  JnaStructureOfC getInfo();
}
