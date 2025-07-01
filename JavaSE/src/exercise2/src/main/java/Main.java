import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int seconds = 0;
        Scanner in = new Scanner(System.in);
        boolean check = false;
        while (!check)
        {
            try
            {
                seconds = input(in, seconds);
                check = true;
            }
            catch (InputMismatchException e)
            {
                System.out.println("Couldn't parse a number. Please, try again");
                in.nextLine();
            }
        }
        int[] arr = new int[3];
        getTime(arr,seconds);
        output(arr, seconds);
        in.close();
    }
    public static int input(Scanner in, int seconds) throws InputMismatchException
    {
        return seconds = in.nextInt();
    }
    public static void getTime(int[] array, int seconds)
    {
        array[0] = seconds / 3600;
        array[1] = (seconds - 3600 * array[0]) / 60;
        array[2] = seconds % 60;
    }
    public static void output(int[] arr, int seconds)
    {
        if (seconds < 0)
        {
            System.out.println("Incorrect time");
        }
        else
        {
            System.out.printf("%02d:%02d:%02d\n", arr[0], arr[1], arr[2]);
        }
    }
}
