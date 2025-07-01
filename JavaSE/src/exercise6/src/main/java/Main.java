import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = 0;
        boolean checkn = false;
        while (!checkn) {
            try {
                n = in.nextInt();
                in.nextLine();
                checkn = true;
            } catch (InputMismatchException e) {
                System.out.println("Couldn't parse a number. Please, try again");
                in.nextLine();
            }
        }
        if (n <= 0) {
            System.out.println("Input error. Size <= 0");
        } else {
            double[] array = new double[n];
            boolean checkArray = false;
            while (!checkArray) {
                try {
                    int i = 0;
                    String line = in.nextLine();
                    String[] numbers = line.split(" ");
                    int length = Math.min(numbers.length, array.length);
                    while (i < length) {
                        array[i] = Double.parseDouble(numbers[i]);
                        i++;
                    }
                    checkArray = true;
                } catch (NumberFormatException e) {
                    System.out.println("Couldn't parse a number. Please, try again");
                }
            }
            sort(array);
            for (double el : array)
            {
                System.out.printf("%.1f ", el);
            }
        }
        in.close();
    }
    public static void sort(double[] array)
    {
        double min,tmp;
        int index = 0;
        for (int i = 0; i < array.length - 1; i++)
        {
            min = array[i];
            for (int j = i + 1; j < array.length; j++)
            {
                if (min > array[j])
                {
                    min = array[j];
                    index = j;
                }
            }
            if (array[i] > min)
            {
                tmp = array[i];
                array[i] = min;
                array[index] = tmp;
            }
        }
    }
}
