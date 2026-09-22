import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
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
        CompletableFuture<?>[] arrayFuture = new CompletableFuture[countAnimals];
        double[] currentTime = new double[countAnimals];
        for (int i = 0; i < lstAnimal.size();i++) {
            int finalI = i;
            currentTime[i] = (double)(System.currentTimeMillis() - startTime) / 1000;
            arrayFuture[i] = CompletableFuture.supplyAsync(() -> lstAnimal.get(finalI).goToWalk()).thenAccept((res) -> System.out.printf("%s, start time = %.2f, end time = %.2f\n",lstAnimal.get(finalI), currentTime[finalI], currentTime[finalI] + res));
        }
        for (int i = 0 ; i < lstAnimal.size(); i++)
        {
            arrayFuture[i].get();
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
