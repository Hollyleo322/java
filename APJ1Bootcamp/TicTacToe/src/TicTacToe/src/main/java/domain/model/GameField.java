package domain.model;

public class GameField {
    private int[][] _field;

    public GameField()
    {
        _field = new int[3][3];
    }
    public int[][] getField()
    {
        return _field;
    }
    public void setField(int[][] field)
    {
        _field = field;
    }
}
