import java.util.concurrent.TimeUnit;

public class Cat extends Animal{
    public Cat(String name, int age)
    {
        super(name,age);
    }

    @Override
    public String toString() {
        return "Cat name = " + getName() +", age = " + getAge();
    }

    @Override
    public double goToWalk() {
        double time = getAge() * 0.25;
        try {
            TimeUnit.SECONDS.sleep(Math.round(time));
        }
        catch (InterruptedException ignore)
        {}
        return time;
    }
}
