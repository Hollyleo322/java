package tbanktraine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Second {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int count = scanner.nextInt();
    scanner.nextLine();
    for (int i = 0; i < count; i++) {
      showWinner(scanner);
    }
  }
  public static void showWinner(Scanner scanner) {
    int size = scanner.nextInt();
    scanner.nextLine();
    List<Integer> list = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      list.add(scanner.nextInt());
    }
    scanner.nextLine();
    list.sort(null);
    boolean check = false;
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i) > (i + 1)) {
        System.out.println("Second");
        check = true;
      }
    }
    if (!check) {
      int sum = 0;
      for (int i = 0; i < list.size(); i++) {
        sum += list.get(i) - (i + 1);
      }
      if (sum % 2 == 0) {
        System.out.println("Second");
      } else {
        System.out.println("First");
      }
    }
  }
}
