import java.util.Comparator;

public class SortAnimal implements Comparator<Animal> {

    @Override
    public int compare(Animal o1, Animal o2) {
        int res;
        if (Herbivore.class.isAssignableFrom(o1.getClass()) && !Herbivore.class.isAssignableFrom(o2.getClass()))
        {
            res = -1;
        } else if (!Herbivore.class.isAssignableFrom(o1.getClass()) && Herbivore.class.isAssignableFrom(o2.getClass())) {
            res = 1;
        }
        else
        {
            res = o1.getName().compareTo(o2.getName());
        }
        return res;
    }
}
