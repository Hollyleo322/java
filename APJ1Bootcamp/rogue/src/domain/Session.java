package domain;

import java.util.Random;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Session {
    private Level _level = new Level();
    private Character _player = new Character();
    private Counter _cnt = new Counter();

    public Session() {
        _level = new Level();
        _player = new Character();
        _cnt = new Counter();
    }

    public Session(Session other) {
        _level = other._level;
        _player = other._player;
        _cnt = other._cnt;
    }

    public Room[] getArrayRooms() {
        return _level.getArrayRooms();
    }

    public Corridor[] getCorridors() {
        return _level.getCorridors();
    }

    public void setArrayRooms(Room[] array) {
        _level.setArrayRooms(array);
    }

    public void setCorridors(Corridor[] corridors) {
        _level.setCorridors(corridors);
    }

    public Level getLevel() {
        return _level;
    }

    public void setLevel(Level level) {
        _level = level;
    }

    public Counter getCounter() {
        return _cnt;
    }

    public void setCounter(Counter cnt) {
        _cnt = cnt;
    }

    @JsonIgnore
    public Point getPlayerPos() {
        return _player.getPos();
    }

    public Character getPlayer() {
        return _player;
    }

    public void setPlayer(Character player) {
        _player = player;
    }

    public String playerMove(Direction dir) {
        String log = _level.moveEvent(dir, _player, _cnt);
        if (_level.checkCoordsOfItems(_player.getPos())) {
            Item it = _level.getItem(_player.getPos(), _player.getBackpack());
            if (it != null) {
                _player.getBackpack().addItem(it, _cnt);
            }
        }
        _player.minusSteps();
        _level.setVisibleorNot(_player.getPos());
        if (_level.isInEntrance(_player.getPos())) {
            _level.unveil(_player.getPos(), _level.getIdRoomOfEntrance(_player.getPos()));
        } else {
            _level.clearUnveiled();
        }
        if (_level.isInCorridor(_player.getPos())) {
            _level.unveilPartOfCorridor(_player.getPos());
        }
        if (_level.isInExit(_player.getPos())) {
            _level.lvlUp();
            genLevel();
        }
        if (_player.getHealth() == 0) {
            _level.lvlOne();
            genLevel();
            _player.reset();
            log = "You died";
        }
        return log;
    }

    public void genLevel() {
        Random random = new Random();
        int room_id = random.nextInt(9);
        _level.genLevel();
        Room cur_room = getArrayRooms()[room_id];
        cur_room.setisVisible(true);
        cur_room.setisVisited(true);
        _player.setX(cur_room.getX() + cur_room.getWidth() / 2);
        _player.setY(cur_room.getY() + cur_room.getHeight() / 2);
        _player.setPreviousPos(
                new Point(cur_room.getX() + cur_room.getWidth() / 2 - 1, cur_room.getY() + cur_room.getHeight() / 2));
        _level.genEnemies(room_id);
        _level.genItems(room_id);
        _level.initExit(room_id);
    }

    public void processingWeapon(int index) {
        if (index == -1) {
            getPlayer().takeOffWeapon();
        } else if (index >= 0 && index < Constants.MAX_ITEMS_IN_BACKPACK
                && !_player.getBackpack().getWeapons().isEmpty()
                && _player.getBackpack().getWeapons().size() > index) {
            Item previos = getPlayer()
                    .takeWeapon(getPlayer().getBackpack().getWeapons().get(index));
            if (previos != null && previos != _player.getCurrentWeapon()) {
                getPlayer().getBackpack().getWeapons().remove(previos);
                getLevel().insertItem(previos, getPlayer().getPreviousPos());
            }
        }
    }

    public void processingFood(int index) {
        if (index >= 0 && index < Constants.MAX_ITEMS_IN_BACKPACK && !_player.getBackpack().getFoods().isEmpty()) {
            _player.eatFood(_player.getBackpack().getFoods().get(index));
            _player.getBackpack().getFoods().remove(index);
            _cnt.incrementEatedFood();
        }
    }

    public void processingElixirs(int index) {
        if (index >= 0 && index < Constants.MAX_ITEMS_IN_BACKPACK && !_player.getBackpack().getElixirs().isEmpty()) {
            _player.useElixirandScrolls(_player.getBackpack().getElixirs().get(index));
            _player.getBackpack().getElixirs().remove(index);
            _cnt.incrementDrinkedElixirs();
        }
    }

    public void processingScrolls(int index) {
        if (index >= 0 && index < Constants.MAX_ITEMS_IN_BACKPACK && !_player.getBackpack().getScrolls().isEmpty()) {
            _player.useElixirandScrolls(_player.getBackpack().getScrolls().get(index));
            _player.getBackpack().getScrolls().remove(index);
            _cnt.incrementReadedScrolls();
        }
    }

}
