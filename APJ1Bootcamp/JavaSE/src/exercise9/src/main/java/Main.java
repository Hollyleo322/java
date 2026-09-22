import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> listStr = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        int n = 0;
        try
        {
            n = in.nextInt();
            in.nextLine();
        }
        catch (InputMismatchException ignore)
        {
            System.out.println("It isn't number");
        }
        for (int i = 0 ; i < n ; i++)
        {
            listStr.add(in.nextLine());
        }
        String substr = in.nextLine();
        StringBuilder buildResult = new StringBuilder();
        for (String val : listStr)
        {
            if (val.contains(substr))
            {
                buildResult.append(val).append(", ");
            }
        }
        if (!buildResult.isEmpty()) {
            buildResult.delete(buildResult.length() - 2, buildResult.length() - 1);
            String result = new String(buildResult);
            System.out.println(result);
        }
        in.close();
    }
}
