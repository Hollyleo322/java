package domain;

import java.util.Vector;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Character {
    private Point _pos = new Point(0, 0);
    private Point _previousPos = new Point(0, 0);
    private int _maximalHealth = 500;
    private int _health = 500;
    private int _dexterity = 70;
    private int _strength = 70;
    private Item _currentWeapon = null;
    private String _name = "";
    private Backpack _backpack;
    private Vector<Item> _usingElixirs;
    private boolean _attacked;
    private boolean _asleep;

    public void reset() {
        _health = 500;
        _maximalHealth = 500;
        _dexterity = 70;
        _strength = 70;
        _currentWeapon = null;
        _backpack.clear();
        _usingElixirs.clear();
        _asleep = false;
        _attacked = false;
    }

    public Point getPos() {
        return _pos;
    }

    public void setPos(Point pos) {
        _pos = pos;
    }

    public void setX(int x) {
        _pos.setX(x);
    }

    public void setY(int y) {
        _pos.setY(y);
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

    public Character() {
        _backpack = new Backpack();
        _usingElixirs = new Vector<>();
        _attacked = false;
        _asleep = false;
    }

    public boolean isAsleep() {
        return _asleep;
    }

    public void toggleAsleep() {
        _asleep = !_asleep;
    }

    public void setAsleep(boolean stat) {
        _asleep = stat;
    }

    public Backpack getBackpack() {
        return _backpack;
    }

    public void setBackpack(Backpack backpack) {
        _backpack = backpack;
    }

    public Point getPreviousPos() {
        return _previousPos;
    }

    public int getMaximalHealth() {
        return _maximalHealth;
    }

    public void setMaximalHealth(int maximalHealth) {
        _maximalHealth = maximalHealth;
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

    public Vector<Item> getUsingElixirs() {
        return _usingElixirs;
    }

    public void setUsingElixirs(Vector<Item> usingElixirs) {
        _usingElixirs = usingElixirs;

    }

    public int getStrength() {
        return _strength;
    }

    public void setStrength(int strength) {
        _strength = strength;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public Item getCurrentWeapon() {
        return _currentWeapon;
    }

    public void setCurrentWeapon(Item weapon) {
        _currentWeapon = weapon;
    }

    public void decreaseHP(int val) {
        if (val > 0) {
            _health -= val;
            if (_health < 0)
                _health = 0;
        }
    }

    public Item takeWeapon(Item weapon) {
        Item res = _currentWeapon;
        setCurrentWeapon(weapon);
        if (res != null) {
            _strength -= res.getStrength();
        }
        _strength += weapon.getStrength();
        return res;
    }

    public void setPreviousPos(Point pos) {
        _previousPos = pos;
    }

    public void takeOffWeapon() {
        if (_currentWeapon != null) {
            Item weapon = getCurrentWeapon();
            setCurrentWeapon(null);
            _strength -= weapon.getStrength();
        }
    }

    public void eatFood(Item food) {
        double value = (double) _health / 100 * food.getHealth();
        if (value < 1.0) {
            value = 1;
        }
        _health += (int) value;
        if (_health > _maximalHealth) {
            _health = _maximalHealth;
        }
    }

    public void useElixirandScrolls(Item item) {
        double value = 0;
        switch (item.getSubtype()) {
            case "Dexterity":
                value = (double) _dexterity / 100 * item.getDexterity();
                if (value < 1.0) {
                    value = 1;
                }
                _dexterity += (int) value;
                item.setDexterity((int) value);
                break;
            case "Strength":
                value = (double) _strength / 100 * item.getStrength();
                if (value < 1.0) {
                    value = 1;
                }
                _strength += (int) value;
                item.setStrength((int) value);
                break;
            case "Maximum HP":
                value = (double) _maximalHealth / 100 * item.getMaximalHealth();
                if (value < 1.0) {
                    value = 1;
                }
                _health += (int) value;
                _maximalHealth += (int) value;
                item.setMaximalHealth((int) value);
                break;
        }
        if (item.getType().equals("Elixir")) {
            _usingElixirs.add(item);
        }

    }

    public void minusSteps() {
        Vector<Item> tmpVector = new Vector<>();
        if (!_usingElixirs.isEmpty()) {
            for (Item elixir : _usingElixirs) {
                elixir.setSteps(elixir.getSteps() - 1);
                if (elixir.getSteps() == 0) {
                    getBackStats(elixir);
                    tmpVector.add(elixir);
                }
            }
        }
        for (Item elixir : tmpVector) {
            _usingElixirs.remove(elixir);
        }
        tmpVector.clear();
    }

    protected void getBackStats(Item elixir) {
        switch (elixir.getSubtype()) {
            case "Dexterity":
                _dexterity -= elixir.getDexterity();
                break;
            case "Strength":
                _strength -= elixir.getStrength();
                break;
            case "Maximum HP":
                _health -= elixir.getMaximalHealth();
                _maximalHealth -= elixir.getMaximalHealth();
                if (_health < 1) {
                    _health = 2;
                }
                break;
        }
    }

    protected int calculateDamage(Enemy enemy) {
        int damage = Constants.INITIAL_DAMAGE;
        if (!(enemy.getType() == "Vampire" && enemy.getVampireFirstAttack())
                && !(enemy.getType() == "Snake" && _asleep)) {
            if (getCurrentWeapon() == null)
                damage += (getStrength() - Constants.STANDART_STRENGTH) * Constants.STRENGTH_FACTOR;
            else {
                damage = getCurrentWeapon().getStrength() * (getStrength() - getCurrentWeapon().getStrength()
                        + Constants.STRENGTH_ADDITION) / 100;
            }
        } else if (enemy.getType() == "Vampire" && enemy.getVampireFirstAttack())
            enemy.setVampireFirstAttack(false);
        else if (enemy.getType() == "Snake" && _asleep)
            _asleep = false;
        return damage;
    }

    protected boolean checkHit(Enemy enemy) {
        boolean was_hit = false;
        int chance = Constants.INITIAL_HIT_CHANCE;
        chance += Room.hitChanceFormula(_dexterity, enemy.getDexterity());
        if (chance > UserRandom.getRandom(0, 100) || enemy.getType() == "Ogre")
            was_hit = true;
        return was_hit;
    }

}
