import java.util.*;

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
        lstAnimal.sort(new SortAnimal());
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
            Animal var = initAnimal(in,false,true);
            checkAge(var,lstAnimal);
        } else if (typeOfAnimal.toLowerCase().matches("dog")) {
            Animal var = initAnimal(in,false, false);
            checkAge(var,lstAnimal);
        }
        else if (typeOfAnimal.toLowerCase().matches("hamster"))
        {
            Animal var = initAnimal(in, true, true);
            checkAge(var,lstAnimal);
        }
        else if (typeOfAnimal.toLowerCase().matches("guinea"))
        {
            Animal var = initAnimal(in, true, false);
            checkAge(var,lstAnimal);
        }
        else
        {
            System.out.println("Incorrect input. Unsupported pet type");
        }
    }
    public static void checkAge(Animal var, List<Animal> lstAnimal)
    {
        if (var.getAge() <= 0)
        {
            System.out.println("Incorrect input. Age <= 0");
        }
        else {
                lstAnimal.add(var);
        }
    }
    public static Animal initAnimal(Scanner in, boolean herbivore, boolean first)
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
        if (!herbivore) {
            if (!first) {
                res = new Dog(name, age);
            } else {
                res = new Cat(name, age);
            }
        }
        else
        {
            if (!first)
            {
                res = new GuineaPig(name, age);
            }
            else
            {
                res = new Hamster(name,age);
            }
        }
        return res;
    }
}
