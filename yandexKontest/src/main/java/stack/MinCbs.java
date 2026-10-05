package stack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class MinCbs {

  public static void main(String[] args) throws IOException {
    try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
      int n = Integer.parseInt(bufferedReader.readLine());
      String w = bufferedReader.readLine();
      String s = bufferedReader.readLine();
      System.out.println(getMinCbs(n, w, s));
    }
  }

  public static String getMinCbs(int n, String w, String s) {
    String result = "";
    if (n != 1) {
      if (s.length() == n) {
        result = s;
      } else if (s.length() < n) {
        Stack<Character> stack = new Stack<>();
        analysisStartStr(s, stack);
        var priority = getArrayPriority(w);
        result = finishStr(s, stack, priority, n);
      }
    }
    return result;
  }

  private static String finishStr(String s, Stack<Character> stack, char[] priority, int n) {
    StringBuilder sb = new StringBuilder(s);
    int diff = sb.length() - stack.size();
    boolean available = stack.size() * 2 < n;
    for (int i = s.length(); i < n && available; i++) {
      boolean pasted = false;
      for (int j = 0; j < 4 && !pasted; j++) {
        pasted = processingByPriority(priority[j], stack, n, sb, diff);
        if (pasted) {
          diff += 2;
        }
        if (diff + stack.size() * 2 >= n) {
          available = false;
          break;
        }
      }
    }
    while (!stack.isEmpty()) {
      char current = stack.pop();
      if (current == '(') {
        sb.append(')');
      } else {
        sb.append(']');
      }
    }
    return sb.toString();
  }

  private static boolean processingByPriority(char current, Stack<Character> stack, int n,
      StringBuilder sb, Integer diff) {
    boolean result = false;
    switch (current) {
      case '(', '[':
        if (diff + (stack.size() + 1) * 2 <= n) {
          stack.push(current);
        }
        break;
      case ']':
        if (!stack.isEmpty() && stack.peek() == '[') {
          sb.append(']');
          stack.pop();
          result = true;
        }
        break;
      case ')':
        if (!stack.isEmpty() && stack.peek() == '(') {
          sb.append(')');
          stack.pop();
          result = true;
        }
        break;
    }
    return result;
  }

  private static char[] getArrayPriority(String w) {
    char[] result = new char[4];
    for (int i = 0; i < 4; i++) {
      result[i] = w.charAt(i);
    }
    return result;
  }

  private static void analysisStartStr(String s, Stack<Character> stack) {
    for (int i = 0; i < s.length(); i++) {
      char current = s.charAt(i);
      if (current == '(' || current == '[') {
        stack.push(current);
      } else if (current == ')' || current == ']') {
        stack.pop();
      }
    }
  }

}
