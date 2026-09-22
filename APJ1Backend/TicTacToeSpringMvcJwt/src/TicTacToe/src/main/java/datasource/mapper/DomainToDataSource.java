package datasource.mapper;

import datasource.model.DSCurrentGame;
import datasource.model.DSGameField;
import domain.model.CurrentGame;
import domain.model.GameField;

public class DomainToDataSource {
    public static DSGameField convertGameFieldtoDS(GameField gameField)
    {
        DSGameField res = new DSGameField();
        res.setField(gameField.getField());
        return res;
    }
    public static DSCurrentGame convertCurrentGametoDS(CurrentGame game)
    {
        return new DSCurrentGame(game.getUuid(), convertGameFieldtoDS(game.getGameField()), game.getCondition(), game.isWithAI(), game.getUserId(), game.getDateOfCreating());
    }
}
