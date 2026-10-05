package stack;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MinCbsTest {
  @Test
  public void firstTest() {
    int n = 6;
    String w = "()[]";
    String s = "([(";
    Assertions.assertEquals("([()])", MinCbs.getMinCbs(n,w,s));
  }
  @Test
  public void secondTest() {
    int n = 6;
    String w = "][)(";
    String s = "([";
    Assertions.assertEquals("([][])", MinCbs.getMinCbs(n, w, s));
  }
  @Test
  public void thirdTest() {
    int n = 4;
    String w = "(][)";
    String s = "()[]";
    Assertions.assertEquals("()[]", MinCbs.getMinCbs(n,w,s));
  }
  @Test
  public void fourthTest() {
    int n = 10;
    String w = ")(][";
    String s = "([(";
    Assertions.assertEquals("([()()()])", MinCbs.getMinCbs(n,w,s));
  }  @Test
  public void fifthTest() {
    int n = 1;
    String w = ")(][";
    String s = "(";
    Assertions.assertEquals("", MinCbs.getMinCbs(n,w,s));
  }
  @Test
  public void sixthTest() {
    int n = 3;
    String w = "()[]";
    String s = "(";
    Assertions.assertEquals("()", MinCbs.getMinCbs(n,w,s));
  }
}
