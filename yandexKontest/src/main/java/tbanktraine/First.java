package tbanktraine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class First {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Integer number = scanner.nextInt();
    System.out.println(minimalNumber(number));
  }
  public static String minimalNumber(Integer number) {
    List<Character> list = new ArrayList<>();
    for (int i = 0 ; i < 4; i++) {
      list.add((char) (number % 10 + '0'));
      number /= 10;
    }
    list.sort(null);
    if (list.get(0) == '0') {
      char minimal = '0';
      for (int i = 0; i < list.size(); i++) {
        if (minimal != list.get(i)) {
          minimal = list.get(i);
          list.remove(i);
          break;
        }
      }
      list.add(0, minimal);
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 4; i++) {
      sb.append(list.get(i));
    }
    return sb.toString();
  }
}