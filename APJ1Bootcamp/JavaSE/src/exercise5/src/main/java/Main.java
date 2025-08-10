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
                    int i = 0;
                    while (i < array.length)
                    {
                        array[i] = in.nextInt();
                        i++;
                    }

                    checkArray = true;
                } catch (InputMismatchException e) {
                    System.out.println("Couldn't parse a number. Please, try again");
                    in.nextLine();
                }
            }
            int[] result = countNumbersWithFirstandLast(array);
            if (result.length > 0) {
                int j = 0;
                while (j < result.length)
                {
                    System.out.printf("%d ", result[j]);
                    j++;
                }
            }
            else
            {
                System.out.println("There are no such elements");
            }
        }
        in.close();
    }
    public static int[] countNumbersWithFirstandLast(int[] arr)
    {
        int i = 0, count = 0;
        while (i < arr.length)
        {
            if (isValidNumber(arr[i]))
            {
                count++;
            }
            i++;
        }
        int[] result = new int[count];
        if (count != 0) {
            int j = 0, k = 0;
            while (j < arr.length)
            {
                if (isValidNumber(arr[j]))
                {
                    result[k] = arr[j];
                    k++;
                }
                j++;
            }
        }
        return result;
    }
    public static boolean isValidNumber(int number)
    {
        int first = number, last = number % 10;
        boolean result = false;
        while (number > 9)
        {
            number /= 10;
            first = number;
        }
        if (first == last)
        {
            result = true;
        }
        return result;
    }
}
