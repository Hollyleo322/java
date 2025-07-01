package web.mapper;
import domain.model.CurrentGame;
import domain.model.GameField;
import web.model.WebCurrentGame;
import web.model.WebGameField;

public class WebToDomain {
    public static GameField convertGameFieldtoDomain(WebGameField gameField)
    {
        GameField res = new GameField();
        res.setField(gameField.getField());
        return res;
    }
    public static CurrentGame convertCurrentGametoDomain(WebCurrentGame currentGame)
    {
        return new CurrentGame(currentGame.getUuid(), convertGameFieldtoDomain(currentGame.getGameField()), currentGame.getCondition(), currentGame.isWithAI(), currentGame.getUserId(), currentGame.getDateOfCreating());
    }
}
