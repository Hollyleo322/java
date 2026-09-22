package presentation;

import java.util.Vector;
import datalayer.Data;
import datalayer.ProcessingSession;
import domain.*;
import jcurses.system.Toolkit;

import java.io.IOException;
import java.util.List;

public class Controller {
    private Session session = new Session();
    private int _currentLevel = 1;
    private Data _data = new Data();

    public Room[] getArrayRooms() {
        return session.getArrayRooms().clone();
    }

    public Corridor[] getCorridors() {
        return session.getCorridors().clone();
    }

    public domain.Character getPlayer() {
        return session.getPlayer();
    }

    public void genLevel() {
        session.genLevel();
    }

    public Point getPlayerPos() {
        return session.getPlayerPos();
    }

    public boolean processUserInput() {
        int key = Toolkit.readCharacter().getCode();
        int index;
        boolean run_flag = true;
        String log = "";
        switch (key) {
            case 'W':
            case 'w':
                log = session.playerMove(Direction.Up);
                break;
            case 'S':
            case 's':
                log = session.playerMove(Direction.Down);
                break;
            case 'A':
            case 'a':
                log = session.playerMove(Direction.Left);
                break;
            case 'D':
            case 'd':
                log = session.playerMove(Direction.Right);
                break;
            case 'Q':
            case 'q':
                run_flag = false;
                exchange(_data, session.getCounter());
                _data.writeDataJson();
                _data.writeGeneralStats();
                break;
            case 'h':
            case 'H':
                index = Presentation.outputBackpackItems(session.getPlayer().getBackpack().getWeapons());
                session.processingWeapon(index);
                break;
            case 'j':
            case 'J':
                index = Presentation.outputBackpackItems(session.getPlayer().getBackpack().getFoods());
                session.processingFood(index);
                break;
            case 'k':
            case 'K':
                index = Presentation.outputBackpackItems(session.getPlayer().getBackpack().getElixirs());
                session.processingElixirs(index);
                break;
            case 'e':
            case 'E':
                index = Presentation.outputBackpackItems(session.getPlayer().getBackpack().getScrolls());
                session.processingScrolls(index);
                break;
            case 'z':
            case 'Z':
                Presentation.outputStatistics(getStatistics());
                break;
            case 'x':
            case 'X':
                ProcessingSession.saveSession(session);
                break;
        }
        Presentation.outputFightLog(log);
        if (_currentLevel != session.getLevel().getLevel() || log == "You died") {
            _currentLevel = session.getLevel().getLevel();
            Presentation.clear();
            exchange(_data, session.getCounter());
            _data.setLvl(_currentLevel);
            _data.writeDataJson();
            if (log == "You died") {
                Presentation.outputEndofDeath();
                Presentation.clear();
            }
        }
        if (_currentLevel == 22) {
            run_flag = false;
            exchange(_data, session.getCounter());
            _data.writeDataJson();
            _data.writeGeneralStats();
            Presentation.outputEndForWinner();
        }
        return run_flag;
    }

    public void load() {
        session = ProcessingSession.loadSession();
    }

    public Vector<String> getPlayerInfo() {
        Vector<String> info = new Vector<>();
        info.add("Level:_" + session.getLevel().getLevel());
        info.add("Name:_" + session.getPlayer().getName());
        info.add("Hp:_" + session.getPlayer().getHealth() + "(" + session.getPlayer().getMaximalHealth() + ")");
        info.add("Dexterity:_" + session.getPlayer().getDexterity());
        info.add("Strength:_" + session.getPlayer().getStrength());
        info.add("Treasures:_" + session.getPlayer().getBackpack().getQuantityTreasure());
        if (session.getPlayer().getCurrentWeapon() != null) {
            info.add("Current_weapon:_" + session.getPlayer().getCurrentWeapon().getSubtype());
        } else {
            info.add("No_weapon");
        }
        return info;
    }

    public Vector<String> getStatistics() {
        List<Data> dt = _data.ListData();
        Vector<String> result = new Vector<>();
        for (Data it : dt) {
            StringBuilder sb = new StringBuilder();
            sb.append("Name-" + it.getName() + "_Treasures-" + it.getCountTreasures() + "_Level-" + it.getLvl()
                    + "_Destroyed_Enemies-"
                    + it.getDestroyedEnemies() + "_Eated_Food-" + it.getEatedFood() + "_Drinked_Elixirs-"
                    + it.getDrinkedElixirs() + "_Readed_Scrolls-" + it.getReadedScrolls() + "_Hits-" + it.getCountHits()
                    + "_Misses- " + it.getCountMisses() + "_Steps-" + it.getCountSteps());
            result.add(sb.toString());
        }
        return result;
    }

    public Vector<String> getElixirsInfo() {
        Vector<String> info = new Vector<>();
        for (Item it : session.getPlayer().getUsingElixirs()) {
            info.add(it.getType() + "_" + it.getSubtype() + "_" + it.getSteps());
        }
        return info;
    }

    public void setName(String name) {
        session.getPlayer().setName(name);
        _data.setName(name);
    }

    public boolean inIntrance() {
        return session.getLevel().isInEntrance(getPlayerPos());
    }

    public Point getExit() {
        return session.getLevel().getExit();
    }

    public void exchange(Data data, Counter count) {
        data.setCountTreasures(count.getCountTreasures());
        data.setDestroyedEnemies(count.getDestroyedEnemies());
        data.setEatedFood(count.getEatedFood());
        data.setDrinkedElixirs(count.getDrinkedElixirs());
        data.setReadedScrolls(count.getReadedScrolls());
        data.setCountHits(count.getCountHits());
        data.setCountMisses(count.getCountMisses());
        data.setCountSteps(count.getCountSteps());
    }

}
