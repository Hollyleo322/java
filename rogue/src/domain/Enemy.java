package domain;

import java.awt.*;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import domain.Level;
import domain.Constants;
import domain.Direction;
import jcurses.system.CharColor;

public class Enemy {
    private Point _pos = new Point(0, 0);
    private String _type;
    private int _health;
    private int _dexterity; // agility
    private int _strength;
    private int _hostility;
    private Direction _dir;
    private boolean _attacked;
    private boolean _ogre_cooldown;
    private boolean _vampire_first_attack = false;

    public void decreaseHP(int val) {
        if (val > 0)
            _health -= val;
    }

    public boolean getVampireFirstAttack() {
        return _vampire_first_attack;
    }

    public void setVampireFirstAttack(boolean val) {
        _vampire_first_attack = val;
    }

    public boolean getAttacked() {
        return _attacked;
    }

    public void setAttacked(boolean val) {
        _attacked = val;

    }

    public void setStatusAttacked() {
        _attacked = true;
    }

    @JsonIgnore
    public boolean isMovedAlready() {
        return _attacked;
    }

    public void endTurn() {
        _attacked = false;
    }

    public String getType() {
        return new String(_type);
    }

    public Point getPos() {
        return _pos;
    }

    public void setPos(Point pos) {
        _pos = pos;
    }

    public int getX() {
        return _pos.getX();
    }

    public int getY() {
        return _pos.getY();
    }

    public boolean getOgreCooldown() {
        return _ogre_cooldown;
    }

    public void setOgreCooldown(boolean val) {
        _ogre_cooldown = val;
    }

    public void setX(int x) {
        _pos.setX(x);
    }

    public void setY(int y) {
        _pos.setY(y);
    }

    public void setType(String type) {
        _type = type;
    }

    public int getHealth() {
        return _health;
    }

    public void setHealth(int health) {
        _health = health;
    }

    public int getDexterity() {
        return _dexterity;
    }

    public void setDexterity(int dexterity) {
        _dexterity = dexterity;
    }

    public int getStrength() {
        return _strength;
    }

    public void setStrength(int strength) {
        _strength = strength;
    }

    public int getHostility() {
        return _hostility;
    }

    public void setHostility(int hostility) {
        _hostility = hostility;
    }

    public Direction getDir() {
        return _dir;
    }

    public void setDir(Direction dir) {
        _dir = dir;
    }

    public Enemy() {
        _pos = new Point(0, 0);
        _type = "";
        _health = 0;
        _dexterity = 0;
        _strength = 0;
        _hostility = 0;
        _dir = Direction.Right;
    }

    public Enemy(int y, int x, String type) {
        _pos.setX(x);
        _pos.setY(y);
        _type = type;
        _dir = Direction.Right;
        _attacked = false;
        _ogre_cooldown = false;
        switch (_type) {
            case "Zombie":
                _hostility = Constants.AVERAGE_HOSTILITY_RADIUS;
                _dexterity = 25;
                _strength = 125;
                _health = 50;
                break;
            case "Vampire":
                _hostility = Constants.HIGH_HOSTILITY_RADIUS;
                _dexterity = 75;
                _strength = 125;
                _health = 50;
                _vampire_first_attack = true;
                break;
            case "Ghost":
                _hostility = Constants.LOW_HOSTILITY_RADIUS;
                _dexterity = 75;
                _strength = 25;
                _health = 75;
                break;
            case "Ogre":
                _hostility = Constants.AVERAGE_HOSTILITY_RADIUS;
                _dexterity = 25;
                _strength = 100;
                _health = 150;
                break;
            case "Snake":
                _hostility = Constants.HIGH_HOSTILITY_RADIUS;
                _dexterity = 100;
                _strength = 30;
                _health = 100;
                break;
            default:
                _hostility = 0;
                _dexterity = 0;
                _strength = 0;
                _health = 0;
        }
    }

    @Override
    public String toString() {
        String res = "&";
        switch (_type) {
            case "Zombie":
                res = "z";
                break;
            case "Vampire":
                res = "v";
                break;
            case "Ghost":
                res = "g";
                break;
            case "Ogre":
                res = "O";
                break;
            case "Snake":
                res = "s";
                break;
            default:
                break;
        }
        return res;

    }

    @JsonIgnore
    public short getColor() {
        short col = CharColor.WHITE;
        switch (_type) {
            case "Zombie":
                col = CharColor.GREEN;
                break;
            case "Vampire":
                col = CharColor.RED;
                break;
            case "Ghost":
                col = CharColor.WHITE;
                break;
            case "Ogre":
                col = CharColor.YELLOW;
                break;
            case "Snake":
                col = CharColor.CYAN;
                break;
            default:
                break;
        }
        return col;
    }

    public static String pickRandomType() {
        int val = UserRandom.getRandom(0, 4);
        String res = "";
        switch (val) {
            case 0:
                res = "Zombie";
                break;
            case 1:
                res = "Vampire";
                break;
            case 2:
                res = "Ghost";
                break;
            case 3:
                res = "Ogre";
                break;
            case 4:
                res = "Snake";
                break;
        }
        return res;
    }

    void move(Level level, Point playerPos) {
        if (!isMovedAlready()) {
            Vector<Direction> path = new Vector<>();
            if (!isPlayerNear(playerPos)) {
                path = pattern(level);
            } else {
                path = distAndNextPosToTarget(playerPos, level);
                path.setSize(1);
            }
            if (!path.isEmpty()) {
                Point cur_coords = new Point(_pos);
                moveCharacterByPath(level, path);
                if (_pos.getY() == playerPos.getY() && _pos.getX() == playerPos.getX()) {
                    _pos.setX(cur_coords.getX());
                    _pos.setY(cur_coords.getY());
                }
                _dir = path.get(path.size() - 1);
            }
        }
        endTurn();
    }

    boolean isPlayerNear(Point playerPos) {
        int dist = Math.abs(playerPos.getX() - getX());
        dist += Math.abs(playerPos.getY() - getY());
        boolean is_near = false;
        switch (_hostility) {
            case Constants.LOW_HOSTILITY_RADIUS:
                if (dist < Constants.LOW_HOSTILITY_RADIUS)
                    is_near = true;
                break;
            case Constants.AVERAGE_HOSTILITY_RADIUS:
                if (dist < Constants.AVERAGE_HOSTILITY_RADIUS)
                    is_near = true;
                break;
            case Constants.HIGH_HOSTILITY_RADIUS:
                if (dist < Constants.HIGH_HOSTILITY_RADIUS)
                    is_near = true;
                break;
        }
        return is_near;
    }

    Vector<Direction> pattern(Level level) {
        Vector<Direction> path = new Vector<>();
        if (_type.contains("Zombie")) {
            for (int i = 0; i < Constants.MAX_TRIES_TO_MOVE && path.isEmpty(); i++) {
                Point cur_coords = new Point(getPos());
                Direction dir = UserRandom.getRandomDir(0, 3);
                level.moveCharacter(dir, cur_coords);
                if (cur_coords.getX() != getX() || cur_coords.getY() != getY()
                        && level.checkUnoccupied(cur_coords)) {
                    path.add(dir);
                }
            }
        } else if (_type.contains("Vampire")) {
            for (int i = 0; i < Constants.MAX_TRIES_TO_MOVE && path.isEmpty(); i++) {
                Point cur_coords = new Point(getPos());
                Direction dir = UserRandom.getRandomDir(0, 7);
                level.moveCharacter(dir, cur_coords);
                if (cur_coords.getX() != getX() || cur_coords.getY() != getY()
                        && level.checkUnoccupied(cur_coords)) {
                    path.add(dir);
                }
            }
        } else if (_type.contains("Ghost")) {
            for (int i = 0; i < Constants.MAX_TRIES_TO_MOVE && path.isEmpty(); i++) {
                Point cur_coords = new Point(getPos());
                Direction dir = UserRandom.getRandomDir(0, 7);
                level.moveCharacter(dir, cur_coords);
                if (cur_coords.getX() != getX() || cur_coords.getY() != getY()
                        && level.checkUnoccupied(cur_coords)) {
                    path.add(dir);
                }
            }
        } else if (_type.contains("Ogre")) {
            for (int i = 0; i < Constants.MAX_TRIES_TO_MOVE && path.isEmpty(); i++) {
                Point cur_coords = new Point(getPos());
                Direction dir = UserRandom.getRandomDir(0, 3);
                level.moveCharacter(dir, cur_coords);
                boolean mv = true;
                for (int j = 0; j < 2 && mv; j++) {
                    if (cur_coords.getX() != getX() || cur_coords.getY() != getY()
                            && level.checkUnoccupied(cur_coords)) {
                        path.add(dir);
                    } else
                        mv = false;
                }
            }
        } else if (_type.contains("Snake")) {
            for (int i = 0; i < Constants.MAX_TRIES_TO_MOVE && path.isEmpty(); i++) {
                Point cur_coords = new Point(getPos());
                Direction dir = UserRandom.getRandomDir(4, 7);
                level.moveCharacter(dir, cur_coords);
                if (cur_coords.getX() != getX() || cur_coords.getY() != getY()
                        && level.checkUnoccupied(cur_coords)) {
                    path.add(dir);
                    _dir = path.get(path.size() - 1);
                }
            }
        }
        return path;
    }

    void moveCharacterByPath(Level level, Vector<Direction> path) {
        for (Direction i : path) {
            level.moveCharacter(i, _pos);
        }
    }

    Vector<Direction> distAndNextPosToTarget(Point target, Level level) {
        Vector<Direction> path = new Vector<>();
        if (target.equals(_pos))
            return path;
        Queue<Point> q = new LinkedList<>();
        q.add(_pos);
        int[] dx = { 0, 0, -1, 1 };
        int[] dy = { -1, 1, 0, 0 };
        Direction[] dirs = { Direction.Up, Direction.Down, Direction.Left, Direction.Right };
        boolean visit[][] = new boolean[Constants.ROOMS_IN_HEIGHT * Constants.REGION_HEIGHT][Constants.ROOMS_IN_WIDTH
                * Constants.REGION_WIDTH];
        int dist[][] = new int[Constants.ROOMS_IN_HEIGHT * Constants.REGION_HEIGHT][Constants.ROOMS_IN_WIDTH
                * Constants.REGION_WIDTH];
        Direction dir_parent[][] = new Direction[Constants.ROOMS_IN_HEIGHT
                * Constants.REGION_HEIGHT][Constants.ROOMS_IN_WIDTH * Constants.REGION_WIDTH];
        Point parent[][] = new Point[Constants.ROOMS_IN_HEIGHT * Constants.REGION_HEIGHT][Constants.ROOMS_IN_WIDTH
                * Constants.REGION_WIDTH];
        for (int i = 0; i < Constants.ROOMS_IN_HEIGHT * Constants.REGION_HEIGHT; i++) {
            for (int j = 0; j < Constants.ROOMS_IN_WIDTH * Constants.REGION_WIDTH; j++) {
                parent[i][j] = new Point(0, 0);
                visit[i][j] = false;
                // dir_parent[i][j] = Direction.Up;
            }
        }
        visit[_pos.getY()][_pos.getX()] = true;
        while (!q.isEmpty()) {
            Point current = q.poll();
            int current_dist = dist[current.getY()][current.getX()];
            for (int i = 0; i < 4; i++) {
                Point coords = new Point(current.getX() + dx[i], current.getY() + dy[i]);
                if (!visit[coords.getY()][coords.getX()] && !level.checkOutside(coords)
                        && level.checkUnoccupied(coords)) {
                    q.add(coords);
                    dist[coords.getY()][coords.getX()] = current_dist + 1;
                    visit[coords.getY()][coords.getX()] = true;
                    parent[coords.getY()][coords.getX()] = current;
                    dir_parent[coords.getY()][coords.getX()] = dirs[i];
                }
            }
        }
        if (visit[target.getY()][target.getX()]) {
            Point coords = new Point(target);
            int maxSteps = Constants.ROOMS_IN_HEIGHT * Constants.REGION_HEIGHT *
                    Constants.ROOMS_IN_WIDTH * Constants.REGION_WIDTH;
            int steps = 0;
            while (!_pos.equals(parent[coords.getY()][coords.getX()]) && steps++ < maxSteps) {
                path.add(dir_parent[coords.getY()][coords.getX()]);
                coords = parent[coords.getY()][coords.getX()];
            }
            path.add(dir_parent[coords.getY()][coords.getX()]);
            Collections.reverse(path);
        }
        return path;
    }

    protected double damageFormula(Character player) {
        double damage = 0;
        if (_type.contains("Zombie") || _type.contains("Ghost") || _type.contains("Snake")) {
            if (_type.contains("Snake")) {
                if (UserRandom.getRandom(0, 100) > Constants.SLEEP_CHANCE) {
                    player.setAsleep(true);
                }
            }
            damage = Constants.INITIAL_DAMAGE
                    + (player.getStrength() - Constants.STANDART_STRENGTH) * Constants.STRENGTH_FACTOR;
        } else if (_type.contains("Vampire")) {
            damage = player.getMaximalHealth() / Constants.MAX_HP_PART;
        } else if (_type.contains("Ogre")) {
            if (!getOgreCooldown()) {
                damage = (player.getStrength() - Constants.STANDART_STRENGTH) * Constants.STRENGTH_FACTOR;
                setOgreCooldown(true);
            } else
                setOgreCooldown(false);
        }

        return damage;
    }

    protected boolean checkHit(Character player) {
        boolean was_hit = false;
        int chance = Constants.INITIAL_HIT_CHANCE;
        chance += Room.hitChanceFormula(_dexterity, player.getDexterity());
        if (chance > UserRandom.getRandom(0, 100) || _type.contains("Ogre"))
            was_hit = true;
        return was_hit;
    }

}
