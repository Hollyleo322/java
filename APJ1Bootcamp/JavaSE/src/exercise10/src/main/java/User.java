public class User {
    public User(String name, int age)
    {
        _name = name;
        _age = age;
    }
    public int getAge()
    {
        return _age;
    }
    public String getName()
    {
        return _name;
    }
    final private String _name;
    final private int _age;

}
