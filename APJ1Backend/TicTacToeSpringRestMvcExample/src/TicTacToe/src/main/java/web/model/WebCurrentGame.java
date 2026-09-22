package web.model;

import java.util.ArrayList;
import java.util.List;

public class WebCurrentGame {
    private int uuid;
    private WebGameField gameField;
    private String condition;
    private boolean withAI = true;
    private List<Integer> userId = new ArrayList<>();

    public WebCurrentGame(int id, WebGameField field, String condition, boolean withAI, List<Integer> users)
    {
        this.uuid = id;
        this.gameField = field;
        this.condition = condition;
        this.withAI = withAI;
        this.userId = users;
    }
    public int getUuid()
    {
        return uuid;
    }
    public WebGameField getGameField()
    {
        return gameField;
    }
    public String getCondition() { return condition;}
    public List<Integer> getUserId() {
        return userId;
    }
    public boolean isWithAI() {return  withAI;}
    public void setUuid(int id)
    {
        uuid = id;
    }
    public void setGameField(WebGameField field)
    {
        gameField = field;
    }
    public void setCondition(String condition) {condition = condition;}
    public void setWithHuman(){
        withAI = false;
    }
    public void setUserId(List<Integer> users) {
        userId = users;
    }
}
