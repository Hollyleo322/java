import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = 0;
        boolean check = false;
        while (!check) {
            try {
                n = in.nextInt();
                in.nextLine();
                check = true;
            } catch (InputMismatchException ignore) {
                System.out.println("Couldn't parse a number. Please, try again");
                in.nextLine();
            }
        }
        if (n != 0) {
            ArrayList<User> userList = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                String name = in.nextLine();
                boolean checkAge = false;
                int age = 0;
                while (!checkAge) {
                    try {
                        age = in.nextInt();
                        in.nextLine();
                        if (age <= 0)
                        {
                            System.out.println("Incorrect input. Age <= 0");
                            i--;
                        }
                        checkAge = true;
                    } catch (InputMismatchException ignore) {
                        System.out.println("Couldn't parse a number. Please, try again");
                        in.nextLine();
                    }
                }
                userList.add(new User(name, age));
            }
            List<User> res = userList.stream().filter(usr -> usr.getAge() >= 18).toList();
            StringBuilder buildResult = new StringBuilder();
            for (User val : res) {
                buildResult.append(val.getName()).append(", ");
            }
            if (!buildResult.isEmpty()) {
                buildResult.delete(buildResult.length() - 2, buildResult.length() - 1);
                String result = new String(buildResult);
                System.out.println(result);
            }
        }
        in.close();
    }
}