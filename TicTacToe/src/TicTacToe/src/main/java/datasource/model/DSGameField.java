package datasource.model;

public class DSGameField {
    private int[][] _field;

    public DSGameField()
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
