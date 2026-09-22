package web.model;

public class WebGameField {
    private int[][] _field;

    public WebGameField()
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
