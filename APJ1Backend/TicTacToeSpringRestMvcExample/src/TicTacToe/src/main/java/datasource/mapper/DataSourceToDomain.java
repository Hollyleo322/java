package datasource.mapper;

import datasource.model.DSCurrentGame;
import datasource.model.DSGameField;
import domain.model.CurrentGame;
import domain.model.GameField;

public class DataSourceToDomain {
    public static GameField convertGameFieldtoDomain(DSGameField gameField)
    {
        GameField res = new GameField();
        res.setField(gameField.getField());
        return res;
    }
    public static CurrentGame convertCurrentGametoDomain(DSCurrentGame currentGame)
    {
        return new CurrentGame(currentGame.getUuid(), convertGameFieldtoDomain(currentGame.getGameField()), currentGame.getCondition(), currentGame.isWithAI(), currentGame.getUserId());
    }
}

