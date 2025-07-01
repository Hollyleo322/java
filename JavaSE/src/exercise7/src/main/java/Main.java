import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String path = in.nextLine();
        File file = new File(path);
        try
        {
            Scanner fileScanner = new Scanner(file);
            int n = 0;
            try
            {
                n = fileScanner.nextInt();
                fileScanner.nextLine();
            }
            catch (InputMismatchException e)
            {
                fileScanner.nextLine();
            }
            if (n > 0)
            {
                processingFile(n,fileScanner);
            }
            else
            {
                System.out.println("Input error. Size <= 0");
            }
            fileScanner.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Input error. File isn't exist");
        }
        in.close();
    }
    public static double min(double[] arr)
    {
        double result = arr[0];
        for (int i = 1; i < arr.length;i++)
        {
            if (result > arr[i])
            {
                result = arr[i];
            }
        }
        return result;
    }
    public static double max(double[] arr)
    {
        double result = arr[0];
        for (int i = 1; i < arr.length;i++)
        {
            if (result < arr[i])
            {
                result = arr[i];
            }
        }
        return result;
    }
    public static void writeData(double min ,double max)
    {
        File result = new File("result.txt");
        if (!result.exists())
        {
            try {
                result.createNewFile();
            }
            catch (IOException e)
            {
                System.out.println("File wasn't created");
            }
        }
        try {
            PrintWriter pw = new PrintWriter(result);
            pw.printf("%.1f %.1f", min, max);
            pw.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File wasn't found");
        }
    }
    public static void printArray(double[] arr)
    {
        for (double el : arr)
        {
            System.out.printf("%.1f ", el);
        }
        System.out.println();
    }
    public static void processingFile(int n, Scanner fileScanner)
    {
        double[] array = new double[n];
        try {
            String line = fileScanner.nextLine();
            String[] numbers = line.split(" ");
            int counter = 0, catchCounter = 0;
            for (String val : numbers)
            {
                try {
                    array[counter++] = Double.parseDouble(val);
                }
                catch (NumberFormatException ignored)
                {
                    counter--;
                    catchCounter++;
                }
            }
            catchCounter = numbers.length - catchCounter;
            if (catchCounter == array.length) {
                double min = min(array);
                double max = max(array);
                System.out.println(n);
                printArray(array);
                System.out.println("Saving min and max values in file");
                writeData(min, max);
            }
            else
            {
                System.out.println("Input error. Insufficient number of elements");
            }
        }
        catch (InputMismatchException e)
        {
            fileScanner.nextLine();
        }
    }

}
