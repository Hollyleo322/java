public abstract class Animal {
    public Animal(String name, int age)
    {
        _name = name;
        _age = age;
    }
    public String getName()
    {
        return _name;
    }
    public int getAge()
    {
        return _age;
    }
    final private String _name;
    final private int _age;
}
