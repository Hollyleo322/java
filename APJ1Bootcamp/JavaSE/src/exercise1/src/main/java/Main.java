import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        double x1 = 0,x2 = 0,x3 = 0,y1 = 0,y2 = 0,y3 = 0;
        boolean check = false;
        while (!check)
        {
            try
            {
                x1 = Double.parseDouble(in.nextLine());
                y1 = Double.parseDouble(in.nextLine());
                x2 = Double.parseDouble(in.nextLine());
                y2 = Double.parseDouble(in.nextLine());
                x3 = Double.parseDouble(in.nextLine());
                y3 = Double.parseDouble(in.nextLine());
                check = true;
            }
            catch (NumberFormatException e)
            {
                System.out.println("Couldn't parse a number. Please, try again");
            }
        }
        double a = Triangle.length(x1, x2, y1, y2);
        double b = Triangle.length(x2, x3, y2, y3);
        double c = Triangle.length(x3, x1, y3, y1);
        if (Triangle.isTriangle(a,b,c)) {
            System.out.printf("Perimeter: %.3f", Triangle.perimetr(a, b, c));
        }
        else
        {
            System.out.println("It isn't triangle");
        }
        in.close();
    }
}
