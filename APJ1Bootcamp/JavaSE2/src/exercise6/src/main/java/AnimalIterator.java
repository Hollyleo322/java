import java.util.List;

public class AnimalIterator implements BaseIterator<Animal> {
    private List<Animal> _lstAnimal;
    private int _index;
    public AnimalIterator(List<Animal> lstAnimal, int index)
    {
        _lstAnimal = lstAnimal;
        _index = index;
    }

    @Override
    public  Animal next() {
        Animal res =_lstAnimal.get(_index);
        _index += 1;
        return res;
    }

    @Override
    public boolean hasNext() {
        return _index + 1 <= _lstAnimal.size();
    }

    @Override
    public void reset() {
        _index = 0;
    }
}
