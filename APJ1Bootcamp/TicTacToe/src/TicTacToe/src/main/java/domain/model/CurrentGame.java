package domain.model;

public class CurrentGame {
    private int _uuid;
    private GameField _gameField;
    public CurrentGame(int id, GameField field)
    {
        _uuid = id;
        _gameField = field;
    }
    public int getUuid()
    {
        return _uuid;
    }
    public GameField getGameField()
    {
        return _gameField;
    }
    public void setUuid(int id)
    {
        _uuid = id;
    }
    public void setGameField(GameField field)
    {
        _gameField = field;
    }
}
