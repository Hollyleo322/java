package stack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class CBS {
  public static void main (String[] args) throws IOException {
    try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
      String cbs = bufferedReader.readLine();
      System.out.println(answerCbs(cbs));
    }
  }
  public static String answerCbs (String cbs) {
    if (isCorrectCbs(cbs)) {
      return "yes";
    }
    return "no";
  }
  public static boolean isCorrectCbs(String cbs) {
    boolean result = true;
    Stack<Character> stack = new Stack<>();
    for (int i = 0; i < cbs.length() && result; i++) {
      Character current = cbs.charAt(i);
      if (current == '{' || current == '(' || current == '[') {
        stack.push(current);
      }
      if (current == '}' || current == ')' || current == ']') {
        if (stack.isEmpty() || !isCorrectOpenBracket(current, stack)){
          result = false;
        }
      }
    }
    if (!stack.isEmpty()) {
      result = false;
    }
    return result;
  }
  public static boolean isCorrectOpenBracket(Character current, Stack<Character> stack) {
    boolean result = true;
    char top = stack.peek();
    switch (current) {
      case '}':
       if (top != '{') {
          result = false;
        }
        break;
      case ']':
        if (top != '['){
          result = false;
        }
        break;
      case ')':
        if (top != '('){
          result = false;
        }
        break;
    }
    stack.pop();
    return result;
  }
}
