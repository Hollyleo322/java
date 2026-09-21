package tbanktraine;


import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Third {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int count = scanner.nextInt();
    scanner.nextLine();
    Set<Integer> numbers = new HashSet<>();
    for (int i = 0; i < count; i++) {
      numbers.add(scanner.nextInt());
    }
    System.out.println((Math.pow(2., numbers.size()) - 1) % (Math.pow(10, 9) + 7));
  }
}
