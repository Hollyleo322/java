import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        List<Animal> lstAnimal = new ArrayList<>();
        int countAnimals = initNumber(in);
        addAnimal(in, lstAnimal, countAnimals);
        lstAnimal.stream().map(animal -> {
            Animal res = animal;
            if (animal.getAge() > 10)
            {
                if (animal instanceof Cat)
                {
                    res = new Cat(animal.getName(), animal.getAge() + 1);
                }
                else
                {
                    res = new Dog(animal.getName(), animal.getAge() +1);
                }
            }
            return res;
        }).forEach(System.out::println);
        in.close();
    }
    public static void addAnimal(Scanner in, List<Animal> lstAnimal, int countAnimals)
    {
        if (countAnimals == 0)
        {
            return;
        }
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
        addAnimal(in,lstAnimal, countAnimals- 1);

    }
    public static Animal initAnimal(Scanner in, boolean cat)
    {
        Animal res;
        String name = in.nextLine();
        int age = initNumber(in);
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
    public static int initNumber(Scanner in)
    {
        int countAnimals;
        try {
            countAnimals = in.nextInt();
            in.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Couldn't parse a number. Please, try again");
            in.nextLine();
            countAnimals = initNumber(in);
        }
        return countAnimals;
    }
}
