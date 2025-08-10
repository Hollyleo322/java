package datasource.model;

public class DSCurrentGame {
    private int _uuid;
    private DSGameField _gameField;
    public DSCurrentGame(int id, DSGameField field)
    {
        _uuid = id;
        _gameField = field;
    }
    public int getUuid()
    {
        return _uuid;
    }
    public DSGameField getGameField()
    {
        return _gameField;
    }
    public void setUuid(int id)
    {
        _uuid = id;
    }
    public void setGameField(DSGameField field)
    {
        _gameField = field;
    }
}
