package datasource.mapper;

import datasource.model.DSCurrentGame;
import datasource.model.DSGameField;
import domain.model.CurrentGame;
import domain.model.GameField;
import domain.model.Roles;

import java.util.ArrayList;
import java.util.List;

public class DataSourceToDomain {
    public static GameField convertGameFieldtoDomain(DSGameField gameField)
    {
        GameField res = new GameField();
        res.setField(gameField.getField());
        return res;
    }
    public static CurrentGame convertCurrentGametoDomain(DSCurrentGame currentGame)
    {
        return new CurrentGame(currentGame.getUuid(), convertGameFieldtoDomain(currentGame.getGameField()), currentGame.getCondition(), currentGame.isWithAI(), currentGame.getUserId(), currentGame.getDateOfCreating());
    }

    public static List<Roles> convertDSEnumToDomain(List<datasource.model.Roles> roles) {
        List<Roles> result = new ArrayList<>();
        roles.forEach((rl) -> result.add(Roles.valueOf(rl.name())));
        return result;
    }
}

