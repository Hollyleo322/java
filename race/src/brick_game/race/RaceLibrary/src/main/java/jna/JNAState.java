package jna;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;

@FieldOrder({"field", "next", "score", "high_score", "level", "speed", "pause"})
public class JNAState extends Structure {

  public static class ByValue extends JNAState implements Structure.ByValue {

  }

  public Pointer field;
  public Pointer next;
  public int score;
  public int high_score;
  public int level;
  public int speed;
  public boolean pause;
}

