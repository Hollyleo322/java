import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int countAnimals = 0;
        boolean checkCountAnimals = false;
        List<Animal> lstAnimal = new ArrayList<>();
        while (!checkCountAnimals) {
            try {
                countAnimals = in.nextInt();
                in.nextLine();
                checkCountAnimals = true;
            } catch (InputMismatchException e) {
                System.out.println("Couldn't parse a number. Please, try again");
                in.nextLine();
            }
        }
        for (int i = 0; i < countAnimals;i++)
        {
            addAnimal(in, lstAnimal);
        }
        for (Animal anml : lstAnimal)
        {
            System.out.println(anml);
        }
        in.close();
    }
    public static void addAnimal(Scanner in, List<Animal> lstAnimal)
    {
        String typeOfAnimal = in.nextLine();
        if (typeOfAnimal.toLowerCase().matches("cat"))
        {
            Animal var = initAnimal(in,true);
            if (var.getAge() <= 0)
            {
                System.out.println("Incorrect input. Age <= 0");
            }
            else
            {
                lstAnimal.add(var);
            }
        } else if (typeOfAnimal.toLowerCase().matches("dog")) {
            Animal var = initAnimal(in, false);
            if (var.getAge() <= 0)
            {
                System.out.println("Incorrect input. Age <= 0");
            }
            else {
                lstAnimal.add(var);
            }
        }
        else
        {
            System.out.println("Incorrect input. Unsupported pet type");
        }
    }
    public static Animal initAnimal(Scanner in, boolean cat)
    {
        Animal res;
        String name = in.nextLine();
        int age = 0;
        boolean checkAge = false;
        while (!checkAge)
        {
            try
            {
                age = in.nextInt();
                in.nextLine();
                checkAge = true;
            }
            catch (InputMismatchException e)
            {
                System.out.println("Couldn't parse a number. Please, try again");
                in.nextLine();
            }
        }
        if (!cat)
        {
            res = new Dog(name,age);
        }
        else
        {
            res = new Cat(name, age);
        }
        return res;
    }
}
