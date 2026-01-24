package structure;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.annotation.Name;
import org.bytedeco.javacpp.annotation.Platform;

@Platform(include = "queue.cpp")
public class Queue {
  @Name("s21::queue<int>")
  public static class PointerQueue extends Pointer {
    static {
      Loader.load();
    }
    public native void allocate();
    public PointerQueue() {
      allocate();
    }
    public native void push(long value);
    public native long pop();
    public native long front();
    public native long back();
    public native boolean empty();
  }
}
