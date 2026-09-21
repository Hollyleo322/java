import java.util.Scanner;

public class House {

  private static int counterWindows = 0;
  private static double counterLightInWindows = 0;
  private static int leftoversCounterWindows = 0;
  private static double leftoversLightInWindows = 0;

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int cels = scanner.nextInt(), appartments = scanner.nextInt(), countWindowX = scanner.nextInt(), countWindowY = scanner.nextInt();
    int counter = 0;
    scanner.nextLine();
    for (int i = 0 ; i < countWindowX * cels; i++) {
      if (isCorrespondsRulesStatic(scanner.nextLine(), countWindowX, countWindowY)) {
        counter += 1;
      }
    }
    System.out.println(counter);
  }
  private static boolean isCorrespondsRulesStatic(String line, int x, int y) {
    boolean result = false;
    if (leftoversCounterWindows != 0) {
      counterWindows += leftoversCounterWindows;
      counterLightInWindows += leftoversLightInWindows;
      leftoversCounterWindows = 0;
      leftoversLightInWindows = 0;
    }
    for (int i = 0; i < line.length(); i++) {
      if (line.charAt(i) == 'X' || line.charAt(i) == '0') {
        if (counterWindows < x * y) {
          if (line.charAt(i) == 'X') {
            counterLightInWindows += 1;
          }
          counterWindows += 1;
        } else {
          if (line.charAt(i) == 'X') {
            leftoversLightInWindows += 1;
          }
          leftoversCounterWindows += 1;
        }
      }
    }
    if (counterWindows == x * y) {
      double check = (double) x * y / 2;
      if (Math.ceil(check) <= counterLightInWindows) {
        result = true;
      }
      counterWindows = 0;
      counterLightInWindows = 0;
    }
    return result;
  }
}
