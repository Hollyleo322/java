import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = 0;
        boolean checkn = false;
        while(!checkn)
        {
            try
            {
                n = in.nextInt();
                checkn = true;
            }
            catch (InputMismatchException e)
            {
                System.out.println("Couldn't parse a number. Please, try again");
                in.nextLine();
            }
        }
        if (n <= 0)
        {
            System.out.println("Input error. Size <= 0");
        }
        else {
            int[] array = new int[n];
            boolean checkArray = false;
            while (!checkArray)
            {
                try {
                    for (int i = 0; i < array.length; i++) {
                        array[i] = in.nextInt();
                    }
                    checkArray = true;
                } catch (InputMismatchException e) {
                    System.out.println("Couldn't parse a number. Please, try again");
                    in.nextLine();
                }
            }
            if (isNegative(array)) {
                System.out.println(arithmeticMean(array));
            }
            else
            {
                System.out.println("There are no negative elements");
            }
        }
        in.close();
    }
    public static boolean isNegative(int[] array)
    {
        boolean result = false;
        for (int el : array)
        {
            if (el < 0)
            {
                result = true;
                break;
            }
        }
        return result;
    }
    public static int arithmeticMean(int[] array)
    {
        int count = 0;
        int n = 0;
        for (int el : array)
        {
            if (el < 0)
            {
                n += el;
                count++;
            }
        }
        return  n / count;
    }
}
