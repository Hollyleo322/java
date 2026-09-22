import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = 0;
        boolean check = false;
        while (!check) {
            try {
                n = in.nextInt();
                check = true;
            }
            catch (InputMismatchException e)
            {
                System.out.println("Couldn't parse a number. Please, try again");
                in.nextLine();
            }
        }
        try
        {
            if (n < 0)
            {
                System.out.println(fibbMinus(n));
            }
            else
            {
                System.out.println(fibbPlus(n));
            }
        }
        catch (StackOverflowError e)
        {
            System.out.println("Too large n");
        }
        in.close();
    }
    public static int fibbPlus(int n) throws StackOverflowError
    {
        if (n == 0)
        {
            return 0;
        }
        if (n == 1)
        {
            return 1;
        }
        return fibbPlus(n - 1) + fibbPlus(n - 2);
    }
    public static int fibbMinus(int n) throws StackOverflowError
    {
        if (n == 0)
        {
            return 0;
        }
        if (n == 1)
        {
            return 1;
        }
        return fibbMinus(n +2) - fibbMinus(n + 1);

    }
}
