package web.mapper;

import domain.model.CurrentGame;
import domain.model.GameField;
import web.model.WebCurrentGame;
import web.model.WebGameField;

public class DomainToWeb {
    public static WebGameField convertGameFieldtoWeb(GameField gameField)
    {
        WebGameField res = new WebGameField();
        res.setField(gameField.getField());
        return res;
    }
    public static WebCurrentGame convertCurrentGametoDS(CurrentGame game)
    {
        return new WebCurrentGame(game.getUuid(), convertGameFieldtoWeb(game.getGameField()), game.getCondition(), game.isWithAI(), game.getUserId(), game.getDateOfCreating());
    }
}
