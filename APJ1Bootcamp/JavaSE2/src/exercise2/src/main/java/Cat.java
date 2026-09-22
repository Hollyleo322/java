public class Cat extends Animal{
    public Cat(String name, int age, double weight)
    {
        super(name,age, weight);
    }

    @Override
    public String toString() {
        String weight = String.format("%.2f", getWeight()).replace(',', '.');
        String feed = String.format("%.2f", getInfoKg()).replace(',', '.');
        return "Cat name = " + getName() +", age = " + getAge() + ", mass = " + weight + ", feed = " + feed;
    }

    @Override
    public double getInfoKg() {
        return getWeight() * 0.1;
    }
}
