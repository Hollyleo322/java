package domain;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;
import java.util.Vector;
import java.util.Set;

public class Room {
    private Point _startPos;
    private int _width;
    private int _height;
    private Vector<Item> _items;
    private Vector<Enemy> _enemies;
    private Deque<Point> _entrances;
    private Vector<Point> _vectorEntrances;
    private Vector<Point> _unveiledPoints;
    private boolean _isVisible;
    private boolean _isVisited;

    public Room() {
        _startPos = new Point(0, 0);
        _width = 0;
        _height = 0;
        _isVisible = false;
        _isVisited = false;
        _items = new Vector<>();
        _enemies = new Vector<>();
        _entrances = new ArrayDeque<>();
        _vectorEntrances = new Vector<>();
        _unveiledPoints = new Vector<>();
    }

    public Room(int index) {
        _width = UserRandom.getRandom(Constants.MIN_ROOM_WIDTH, Constants.MAX_ROOM_WIDTH);
        _height = UserRandom.getRandom(Constants.MIN_ROOM_HEIGHT, Constants.MIN_ROOM_HEIGHT);
        int left_range_coord = (index % Constants.ROOMS_IN_WIDTH) * Constants.REGION_WIDTH + 1;
        int right_range_coord = (index % Constants.ROOMS_IN_WIDTH + 1) * Constants.REGION_WIDTH - _width - 1;
        int up_range_coord = (index / Constants.ROOMS_IN_WIDTH) * Constants.REGION_HEIGHT + 1;
        int bottom_range_coord = (index / Constants.ROOMS_IN_WIDTH + 1) * Constants.REGION_HEIGHT - _height - 1;
        _startPos = new Point(UserRandom.getRandom(left_range_coord, right_range_coord),
                UserRandom.getRandom(up_range_coord, bottom_range_coord));

        _enemies = new Vector<>();
        _items = new Vector<>();
        _entrances = new ArrayDeque<>();
        _vectorEntrances = new Vector<>();
        _unveiledPoints = new Vector<>();
        _isVisible = false;
        _isVisited = false;

    }

    public Point getStartPos() {
        return _startPos;
    }

    public void setStartPos(Point startPos) {
        _startPos = startPos;
    }

    public void clearUnveiled() {
        _unveiledPoints.clear();
    }

    public boolean getisVisible() {
        return _isVisible;
    }

    public void setisVisible(boolean isVisible) {
        _isVisible = isVisible;
    }

    public boolean getisVisited() {
        return _isVisited;
    }

    public void setisVisited(boolean isVisited) {
        _isVisited = isVisited;
    }

    public void setInvisible() {
        _isVisible = false;
    }

    public void setUnveiledPoints(Vector<Point> unveiledPoints) {
        _unveiledPoints = unveiledPoints;
    }

    public void setUnveiledPointsOfSet(Set<Point> points) {
        _unveiledPoints.addAll(points);
    }

    public Vector<Point> getUnveiledPoints() {
        return _unveiledPoints;
    }

    public Point popEntrance() {
        return _entrances.pop();
    }

    public int getWidth() {
        return _width;
    }

    public void setWidth(int width) {
        _width = width;
    }

    public int getHeight() {
        return _height;
    }

    public void setHeight(int height) {
        _height = height;
    }

    public int getX() {
        return _startPos.getX();
    }

    public int getY() {
        return _startPos.getY();
    }

    public void setX(int x) {
        _startPos.setX(x);
    }

    public void setY(int y) {
        _startPos.setY(y);
    }

    public Vector<Enemy> getEnemies() {
        return _enemies;
    }

    public void setEnemies(Vector<Enemy> enemies) {
        _enemies = enemies;
    }

    public Vector<Item> getItems() {
        return _items;
    }

    public void setItems(Vector<Item> items) {
        _items = items;
    }

    public Deque<Point> getEntrances() {
        return _entrances;
    }

    public void setEntrances(Deque<Point> entrances) {
        _entrances = entrances;
    }

    public Vector<Point> getVectorEntrances() {
        return _vectorEntrances;
    }

    public void setVectorEntrances(Vector<Point> vectorEntrances) {
        _vectorEntrances = vectorEntrances;
    }

    public boolean isUnveiled(int x, int y) {
        boolean res = false;
        for (Point pt : _unveiledPoints) {
            if (pt.getX() == x && pt.getY() == y) {
                res = true;
                break;
            }
        }
        return res;
    }

    public void makeRightEntrance() {
        int x = getX() + getWidth() - 1;
        int y = UserRandom.getRandom(getY() + 1, getY() + getHeight() - 2);
        _entrances.addLast(new Point(x, y));
        _vectorEntrances.add(new Point(x, y));
    }

    public void makeLeftEntrance() {
        int x = getX();
        int y = UserRandom.getRandom(getY() + 1, getY() + getHeight() - 2);
        _entrances.addLast(new Point(x, y));
        _vectorEntrances.add(new Point(x, y));

    }

    public void makeDownEntrance() {
        int x = UserRandom.getRandom(getX() + 1, getX() + getWidth() - 2);
        int y = getY() + getHeight() - 1;
        _entrances.addLast(new Point(x, y));
        _vectorEntrances.add(new Point(x, y));
    }

    public Direction getDirection(Point pos) {
        boolean isFound = false;
        Direction res = Direction.Error;
        pos.setY(pos.getY() - 1);
        if (!isPointOutideRoom(pos)) {
            isFound = true;
            res = Direction.Up;
        } else {
            pos.setY(pos.getY() + 2);
            if (!isPointOutideRoom(pos)) {
                isFound = true;
                res = Direction.Down;
                pos.setY(pos.getY() - 1);
            }
        }
        pos.setY(pos.getY() - 1);
        if (!isFound) {
            pos.setX(pos.getX() - 1);
            if (!isPointOutideRoom(pos)) {
                isFound = true;
                res = Direction.Left;
            } else {
                pos.setX(pos.getX() + 2);
                if (!isPointOutideRoom(pos)) {
                    isFound = true;
                    res = Direction.Right;
                }
            }
        }
        return res;
    }

    public void makeUpEntrance() {
        int x = UserRandom.getRandom(getX() + 1, getX() + getWidth() - 2);
        int y = getY();
        _entrances.addLast(new Point(x, y));
        _vectorEntrances.add(new Point(x, y));
    }

    public boolean isPointOutideRoom(Point npos) {
        return (npos.getX() >= getX() + _width - 1) ||
                (npos.getX() <= getX()) ||
                (npos.getY() <= getY()) ||
                (npos.getY() >= getY() + _height - 1);
    }

    public boolean isPointOutsideEntrances(Point npos) {
        for (int i = 0; i < getVectorEntrances().size(); i++) {
            if (getVectorEntrances().get(i).getX() == npos.getX() &&
                    getVectorEntrances().get(i).getY() == npos.getY())
                return false;
        }
        return true;
    }

    public void genEnemies(int max_enemies) {
        Random random = new Random();
        int count_monsters = UserRandom.getRandom(0, max_enemies);
        for (int i = 0; i < count_monsters; i++) {
            Point coords;
            do {
                coords = generateCoordsOfEntity();
            } while (!checkUnoccupied(coords));
            _enemies.add(new Enemy(coords.getY(), coords.getX(), Enemy.pickRandomType()));
        }
    }

    public void setExit(Point exit) {
        Point coord;
        do {
            coord = generateCoordsOfEntity();
        } while (!checkUnoccupied(coord));
        exit.setX(coord.getX());
        exit.setY(coord.getY());
    }

    public void genItems(int max_items) {
        Random random = new Random();
        int count_items = random.nextInt(max_items);
        for (int i = 0; i < count_items; i++) {
            Point coords = new Point(0, 0);
            do {
                coords = generateCoordsOfEntity();
            } while (!checkUnoccupiedForItems(coords));
            _items.add(new Item(coords.getX(), coords.getY()));
        }
    }

    private Point generateCoordsOfEntity() {
        Point res = new Point(0, 0);
        int upper_left_x = getX() + 1;
        int upper_left_y = getY() + 1;
        int bottom_right_x = getX() + getWidth() - 2;
        int bottom_right_y = getY() + getHeight() - 2;
        Random random = new Random();
        res.setX(UserRandom.getRandom(upper_left_x, bottom_right_x));
        res.setY(UserRandom.getRandom(upper_left_y, bottom_right_y));
        return res;
    }

    protected boolean checkUnoccupied(Point coords) {
        boolean res = true;
        for (int i = 0; i < _enemies.size() && res; i++) {
            if (_enemies.get(i).getPos().getX() == coords.getX() && _enemies.get(i).getPos().getY() == coords.getY())
                res = false;
        }
        return res;
    }

    public void moveEnemies(Level level, Point player_pos) {
        for (Enemy e : _enemies)
            e.move(level, player_pos);
    }

    private boolean checkUnoccupiedForItems(Point coords) {
        boolean res = true;

        for (int i = 0; i < _enemies.size() && res; i++) {
            if (_enemies.get(i).getPos().getX() == coords.getX() && _enemies.get(i).getPos().getY() == coords.getY())
                res = false;
        }
        if (res) {
            for (int i = 0; i < _items.size() && res; i++) {
                if (_items.get(i).getPos().getX() == coords.getX() && _items.get(i).getPos().getY() == coords.getY())
                    res = false;
            }
        }
        return res;
    }

    protected String fightIfAble(Character player, Point attack_pos, Counter cnt, int lvl, Level level) {
        String log = "";
        for (int i = 0; i < _enemies.size(); i++) {
            if (Math.max(Math.abs(_enemies.get(i).getPos().getX() - player.getPos().getX()),
                    Math.abs(_enemies.get(i).getPos().getY() - player.getPos().getY())) == 1) {
                _unveiledPoints.add(_enemies.get(i).getPos());
                if (level.isInCorridor(_enemies.get(i).getPos())) {
                    level.unveilPartOfCorridor(_enemies.get(i).getPos());
                }
                boolean player_attacked = _enemies.get(i).getPos().getX() == attack_pos.getX()
                        && _enemies.get(i).getPos().getY() == attack_pos.getY();
                log = log.concat(fight(i, player, player_attacked, cnt, lvl));
            }
        }
        return log;
    }

    protected String fight(int enemy_id, Character player, boolean player_attacked, Counter cnt, int lvl) {
        String log = "";
        boolean enemy_died = false;
        if (player_attacked) {
            player.setStatusAttacked();
            if (player.checkHit(_enemies.get(enemy_id))) {
                log = log.concat("You\nattacked!\n");
                cnt.incremetHits();
                int damage = player.calculateDamage(_enemies.get(enemy_id));
                _enemies.get(enemy_id).decreaseHP(damage);
                if (_enemies.get(enemy_id).getHealth() <= 0) {
                    log = log.concat(String.format("%s\ndied!\n", _enemies.get(enemy_id).getType()));
                    cnt.incrementDestroyedEnemies();
                    _items.add(new Item(_enemies.get(enemy_id).getX(), _enemies.get(enemy_id).getY(), lvl));
                    enemy_died = true;
                    _enemies.remove(enemy_id);
                }
            } else {
                log = log.concat("You\nmissed!\n");
                cnt.incremetMisses();
            }
        }
        if (!enemy_died) {
            _enemies.get(enemy_id).setStatusAttacked();
            if (_enemies.get(enemy_id).checkHit(player)) {
                log = log.concat(String.format("%s\nattacked!\n", _enemies.get(enemy_id).getType()));
                int damage = (int) _enemies.get(enemy_id).damageFormula(player);
                player.decreaseHP(damage);
            } else
                log = log.concat(String.format("%s\nmissed!\n", _enemies.get(enemy_id).getType()));
        }
        return log;
    }

    public static double hitChanceFormula(int attacker_dex, int target_dex) {
        return (attacker_dex - target_dex - Constants.STANDART_AGILITY) * Constants.AGILITY_FACTOR;
    }
}