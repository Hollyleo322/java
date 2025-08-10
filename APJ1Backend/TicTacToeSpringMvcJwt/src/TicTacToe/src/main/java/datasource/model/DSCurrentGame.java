package datasource.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class DSCurrentGame {

    private @Id int uuid;
    private DSGameField gameField;
    private String condition;
    private boolean withAI = true;
    private List<Integer> userId = new ArrayList<>();
    private Date dateOfCreating;

    public DSCurrentGame() {
        this.gameField = new DSGameField();
    }
    public DSCurrentGame(int uuid, DSGameField gameField, String condition, boolean withAI, List<Integer> users, Date dateOfCreating)
    {
        this.uuid = uuid;
        this.gameField = gameField;
        this.condition = condition;
        this.withAI = withAI;
        this.userId = users;
        this.dateOfCreating = dateOfCreating;
    }

    public String getCondition() {
        return condition;
    }
    public int getUuid()
    {
        return uuid;
    }
    public DSGameField getGameField()
    {
        return gameField;
    }
    public List<Integer> getUserId(){
        return userId;
    }
    public Date getDateOfCreating() { return  dateOfCreating;};
    public boolean isWithAI() {
        return withAI;
    }
    public void setUuid(int id)
    {
        uuid = id;
    }
    public void setGameField(DSGameField field)
    {
        gameField = field;
    }
    public void setCondition(String condition) {
        this.condition = condition;
    }
    public void setWithHuman(){
        withAI = false;
    }
    public void setUserId(List<Integer> users) {
        userId = users;
    }
    public void setDateOfCreating(Date dateOfCreating) { this.dateOfCreating = dateOfCreating;}
}
