package structure;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Name;
import org.bytedeco.javacpp.annotation.Platform;

@Platform(include= "stack_int.cpp")
public class Stack {
  @Name("s21::stack<int>")
  public static class PointerStack extends Pointer {
    static {
      Loader.load();
    }
    public native void allocate();
    public PointerStack() {
      allocate();
    }
    public native void push(long value);
    public native long pop();
    public native long top();
    public native boolean empty();
  }
}
