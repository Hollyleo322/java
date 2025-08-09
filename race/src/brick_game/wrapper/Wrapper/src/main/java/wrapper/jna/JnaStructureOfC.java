package wrapper.jna;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;
import wrapper.constant.Constants;


@FieldOrder({"field", "next", "score", "high_score", "level", "speed", "pause"})
public class JnaStructureOfC extends Structure {

  public static class ByValue extends JnaStructureOfC implements Structure.ByValue {

  }

  public Pointer field;
  public Pointer next;
  public int score;
  public int high_score;
  public int level;
  public int speed;
  public int pause;

}
