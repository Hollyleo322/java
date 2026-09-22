import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean check = false;
        int number = 0, previous = 0, counter = -1;
        while (!check)
        {
            try
            {
                if (counter >= 0)
                {
                    previous = number;
                }
                number = in.nextInt();
                counter += 1;
                if (number < previous)
                {
                    System.out.printf("The sequence is not ordered from the ordinal number of the number %d\n", counter);
                    check = true;
                }
            }
            catch (InputMismatchException ignore)
            {
                if (counter < 0)
                {
                    System.out.println("Input error");
                }
                else
                {
                    System.out.println("The sequence is ordered in ascending order");
                }
                check = true;
                in.nextLine();
            }
        }
        in.close();
    }
}
