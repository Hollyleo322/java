package stack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class MinCbs {

  public static void main(String[] args) throws IOException {
    try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))){
      int n = Integer.parseInt(bufferedReader.readLine());
      String w = bufferedReader.readLine();
      String s = bufferedReader.readLine();
      if (s.length() == n) {
        System.out.println(s);
      }
      else {
        boolean isRoundFirst = setPriority(w);
        boolean isOpenFirst = setOpenPriority(w, isRoundFirst);
        System.out.println(collectCbs(s, n, isRoundFirst, isOpenFirst));
      }
    }
  }
  private static boolean setPriority (String priority) {
    boolean isRoundFirst = true;
    for (int i = 0; i < priority.length() && isRoundFirst; i++) {
      if (priority.charAt(i) == '('){
        break;
      }
      if (priority.charAt(i) == '['){
        isRoundFirst = false;
      }
    }
    return isRoundFirst;
  }
  private static boolean setOpenPriority (String priority, boolean isRoundFirst) {
    if (isRoundFirst) {
      return roundPriority(priority);
    }
    return squarePriority(priority);
  }
  private static boolean roundPriority(String priority){
    boolean result = true;
    for (int i = 0; i < priority.length() && result; i++) {
      if (priority.charAt(i) == '(') {
        break;
      }
      if (priority.charAt(i) ==')'){
        result = false;
      }
    }
    return result;
  }
  private static boolean squarePriority(String priority){
    boolean result = true;
    for (int i = 0; i < priority.length() && result; i++) {
      if (priority.charAt(i) == '[') {
        break;
      }
      if (priority.charAt(i) ==']'){
        result = false;
      }
    }
    return result;
  }
  private static String collectCbs(String s, int n , boolean isRoundFirst, boolean isOpenFirst){
    StringBuilder stringBuilder = new StringBuilder(s);

    while (!stack.isEmpty()){
      stringBuilder.append(stack.pop());
    }
    return stringBuilder.toString();
  }
}
