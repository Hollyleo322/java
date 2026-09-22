public abstract class Animal {
    public Animal(String name, int age, double weight)
    {
        _name = name;
        _age = age;
        _weight = weight;
    }
    public String getName()
    {
        return _name;
    }
    public int getAge()
    {
        return _age;
    }
    public double getWeight(){return  _weight;}
    public abstract double getInfoKg();
    private String _name;
    private int _age;
    private double _weight;
}
