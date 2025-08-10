package web.model;

public class WebCurrentGame {
    private int _uuid;
    private WebGameField _gameField;

    public WebCurrentGame(int id, WebGameField field)
    {
        _uuid = id;
        _gameField = field;
    }
    public int getUuid()
    {
        return _uuid;
    }
    public WebGameField getGameField()
    {
        return _gameField;
    }
    public void setUuid(int id)
    {
        _uuid = id;
    }
    public void setGameField(WebGameField field)
    {
        _gameField = field;
    }
}
