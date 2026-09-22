package domain;

import java.util.Vector;
import java.util.Set;
import java.util.HashSet;
import java.util.Random;

import jcurses.system.CharColor;
import jcurses.system.Toolkit;

public class Level {
    private Room[] _arrayRooms;
    private Corridor[] _corridors;
    private int _level_num = 1;
    private Point _exit = new Point(0, 0);

    private int maxItems() {
        int res = 0;
        if (_level_num < 8) {
            res = 3;
        } else if (_level_num >= 8 && _level_num < 15) {
            res = 2;
        } else {
            res = 1;
        }
        return res;
    }

    public void lvlUp() {
        _level_num += 1;
    }

    public void lvlOne() {
        _level_num = 1;
    }

    public void genLevel() {
        _arrayRooms = new Room[9];
        Graph _graph = new Graph();
        for (int i = 0; i < Constants.ROOMS_NUM; i++) {
            Room rm = new Room(i);
            _arrayRooms[i] = rm;
        }
        _corridors = new Corridor[_graph.size()];
        int counterCorridors = 0;
        for (int i = 0; i < Constants.ROOMS_NUM; i++) {
            for (int it : _graph.getConnectedRooms().get(i)) {
                BuilderEntrances.buildEntrances(_arrayRooms[i], _arrayRooms[it], i, it);
                if (it - i == 1) {
                    _corridors[counterCorridors++] = new Corridor(_arrayRooms[i].popEntrance(),
                            _arrayRooms[it].popEntrance(), false);
                } else {
                    _corridors[counterCorridors++] = new Corridor(_arrayRooms[i].popEntrance(),
                            _arrayRooms[it].popEntrance(), true);
                }
            }
        }
    }

    public void genEnemies(int player_room) {
        int max_enemies = Constants.MAX_MONSTERS_PER_ROOM + _level_num / Constants.LEVEL_UPDATE_DIFFICULTY;
        for (int room = 0; room < Constants.ROOMS_NUM; room++) {
            if (room != player_room)
                _arrayRooms[room].genEnemies(max_enemies);
        }
    }

    public boolean isInExit(Point player_pos) {
        boolean res = false;
        if (player_pos.getX() == _exit.getX() && player_pos.getY() == _exit.getY()) {
            res = true;
        }
        return res;
    }

    public void genItems(int player_room) {
        int max_items = maxItems();
        for (int room = 0; room < Constants.ROOMS_NUM; room++) {
            if (room != player_room)
                _arrayRooms[room].genItems(max_items);
        }
    }

    public Level() {
        genLevel();
    }

    public Point getExit() {
        return _exit;
    }

    public void setExit(Point exit) {
        _exit = exit;
    }

    public Room[] getArrayRooms() {
        return _arrayRooms;
    }

    public Corridor[] getCorridors() {
        return _corridors;
    }

    public void setArrayRooms(Room[] array) {
        _arrayRooms = array;
    }

    public void setCorridors(Corridor[] corridors) {
        _corridors = corridors;
    }

    public int getLevel() {
        return _level_num;
    }

    public void setLevel(int lvl) {
        _level_num = lvl;
    }

    public void initExit(int player_room) {
        Random rnd = new Random();
        int exit_room = player_room;
        while (player_room == exit_room) {
            exit_room = rnd.nextInt(Constants.ROOMS_NUM);
        }
        _arrayRooms[exit_room].setExit(_exit);
    }

    public boolean checkOutside(Point npos) {
        boolean outside = true;
        for (int i = 0; i < Constants.ROOMS_NUM && outside; i++)
            outside = _arrayRooms[i].isPointOutideRoom(npos);
        for (int i = 0; i < _corridors.length && outside; i++)
            outside = _corridors[i].isPointOutideCorridor(npos);
        for (int i = 0; i < Constants.ROOMS_NUM && outside; i++)
            outside = _arrayRooms[i].isPointOutsideEntrances(npos);
        return outside;
    }

    public void moveCharacter(Direction dir, Point pos) {
        if (dir == null)
            return;
        Point cur_pos = new Point(pos);
        switch (dir) {
            case Up:
                pos.setY(cur_pos.getY() - 1);
                break;
            case Down:
                pos.setY(cur_pos.getY() + 1);
                break;
            case Left:
                pos.setX(cur_pos.getX() - 1);
                break;
            case Right:
                pos.setX(cur_pos.getX() + 1);
                break;
            case UpRight:
                pos.setX(cur_pos.getX() + 1);
                pos.setY(cur_pos.getY() - 1);
                break;
            case UpLeft:
                pos.setX(cur_pos.getX() - 1);
                pos.setY(cur_pos.getY() - 1);
                break;
            case DownRight:
                pos.setX(cur_pos.getX() + 1);
                pos.setY(cur_pos.getY() + 1);
                break;
            case DownLeft:
                pos.setX(cur_pos.getX() - 1);
                pos.setY(cur_pos.getY() + 1);
                break;
            default:
                break;
        }
        if (checkOutside(pos)) {
            pos.setX(cur_pos.getX());
            pos.setY(cur_pos.getY());
        }
    }

    String moveEvent(Direction player_dir, Character player, Counter cnt) {
        String log = processFights(player, player_dir, cnt);
        Point tmp = new Point(player.getPos());
        if (!player.isMovedAlready())
            moveCharacter(player_dir, player.getPos());
        player.endTurn();
        if (tmp.getX() != player.getPos().getX() || tmp.getY() != player.getPos().getY()) {
            player.getPreviousPos().setX(tmp.getX());
            player.getPreviousPos().setY(tmp.getY());
            cnt.incrementCountSteps();
        }
        for (int i = 0; i < _arrayRooms.length; i++) {
            _arrayRooms[i].moveEnemies(this, player.getPos());
        }
        return log;
    }

    String processFights(Character player, Direction player_dir, Counter cnt) {
        String fight_log = "";
        Point attack_pos = new Point(player.getPos());
        moveCharacter(player_dir, attack_pos);
        for (int i = 0; i < _arrayRooms.length; i++) {
            fight_log = fight_log.concat(_arrayRooms[i].fightIfAble(player, attack_pos, cnt, _level_num, this));
        }
        return fight_log;
    }

    public boolean checkUnoccupied(Point pos) {
        boolean unoccupied = true;
        for (int i = 0; i < _arrayRooms.length && unoccupied; i++) {
            unoccupied = _arrayRooms[i].checkUnoccupied(pos);
        }
        return unoccupied;
    }

    public boolean checkCoordsOfItems(Point pos) {
        boolean res = false;
        for (int i = 0; i < Constants.ROOMS_NUM && !res; i++) {
            for (Item it : _arrayRooms[i].getItems()) {
                if (pos.getX() == it.getX() && pos.getY() == it.getY()) {
                    res = true;
                }
            }
        }
        return res;
    }

    public boolean isInEntrance(Point pos) {
        boolean res = false;
        for (int i = 0; i < Constants.ROOMS_NUM && !res; i++) {
            for (Point entrance : _arrayRooms[i].getVectorEntrances()) {
                if (pos.getX() == entrance.getX() && pos.getY() == entrance.getY()) {
                    res = true;
                }
            }
        }
        return res;
    }

    public boolean isInCorridor(Point pos) {
        boolean res = false;
        for (int i = 0; i < _corridors.length && !res; i++) {
            for (Point cor : _corridors[i].getPoints()) {
                if (pos.getX() == cor.getX() && pos.getY() == cor.getY()) {
                    res = true;
                }
            }
        }
        return res;
    }

    public void unveilPartOfCorridor(Point pos) {
        for (int i = 0; i < _corridors.length; i++) {
            for (Point cor : _corridors[i].getPoints()) {
                if (pos.getX() == cor.getX() && pos.getY() == cor.getY()) {
                    _corridors[i].addToUnveiled(_corridors[i].getPoints().indexOf(cor));
                }
            }
        }
    }

    public int getIdRoomOfEntrance(Point pos) {
        int res = -1;
        for (int i = 0; i < Constants.ROOMS_NUM; i++) {
            if (res != -1) {
                break;
            }
            for (Point entrance : _arrayRooms[i].getVectorEntrances()) {
                if (pos.getX() == entrance.getX() && pos.getY() == entrance.getY()) {
                    res = i;
                }
            }
        }
        return res;
    }

    public void unveil(Point pos, int index) {
        Direction dir = _arrayRooms[index].getDirection(new Point(pos));
        Set<Point> points = new HashSet<>();
        points = RayCastingAndBrezenhem.useRayCasting(new Point(pos), dir, _arrayRooms[index]);
        _arrayRooms[index].setUnveiledPointsOfSet(points);
    }

    public void clearUnveiled() {
        for (int i = 0; i < Constants.ROOMS_NUM; i++) {
            _arrayRooms[i].clearUnveiled();
        }
    }

    public void setVisibleorNot(Point pos) {
        for (Room rm : _arrayRooms) {
            if (rm.isPointOutideRoom(pos)) {
                rm.setInvisible();
            } else {
                rm.setisVisible(true);
                rm.setisVisited(true);
            }
        }
    }

    public Item getItem(Point pos, Backpack bp) {
        Item res = null;
        boolean delete = false;
        for (int i = 0; i < Constants.ROOMS_NUM && !delete; i++) {
            for (Item it : _arrayRooms[i].getItems()) {
                if (pos.getX() == it.getX() && pos.getY() == it.getY()) {
                    if (!bp.isFull(it)) {
                        res = it;
                        delete = true;
                    }
                }
            }
            if (delete) {
                _arrayRooms[i].getItems().remove(res);
            }
        }
        return res;
    }

    public void insertItem(Item it, Point previousPos) {
        it.setX(previousPos.getX());
        it.setY(previousPos.getY());
        _arrayRooms[0].getItems().add(it);
    }
}