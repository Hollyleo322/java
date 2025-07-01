package domain;

import java.util.Random;

public class Item {
    private Point _pos;
    private String _type;
    private String _subtype = "";
    private int _health = 0; // (количество единиц повышения, для еды)
    private int _maximalHealth = 0; // (количество единиц повышения, для свитков и эликсиров, вместе с этим
    // повышается и сам уровень здоровья)
    private int _dexterity = 0;
    private int _strength = 0;
    private int _price = 0; // (для сокровищ)
    private int _steps = 0; // (для эликсиров)

    private void initTreasure(int lvl) {
        Random rnd = new Random();
        _price = rnd.nextInt(lvl * 10);
    }

    private void initFood() {
        Random rnd = new Random();
        _subtype = Constants.FOODS[rnd.nextInt(Constants.FOODS.length)];
        _health = rnd.nextInt(Constants.MAX_PERCENT_FOOD_REGEN_FROM_HEALTH + 1);

    }

    private void initElixirsandScrolls() {
        Random rnd = new Random();
        _subtype = Constants.SUBTYPE_ITEMS[rnd.nextInt(3)];
        switch (_subtype) {
            case "Dexterity":
                _dexterity = rnd.nextInt(Constants.MAX_PERCENT_AGILITY_INCREASE + 1);
                break;
            case "Strength":
                _strength = rnd.nextInt(Constants.MAX_PERCENT_STRENGTH_INCREASE + 1);
                break;
            case "Maximum HP":
                _maximalHealth = rnd.nextInt(Constants.MAX_PERCENT_MAXHP_INCREASE + 1);
                break;
        }
        _steps = UserRandom.getRandom(Constants.MIN_ELIXIR_DURATION_STEPS, Constants.MAX_ELIXIR_DURATION_STEPS);
    }

    private void initWeapon() {
        Random rnd = new Random();
        _subtype = Constants.WEAPONS[rnd.nextInt(Constants.WEAPONS.length)];
        _strength = UserRandom.getRandom(Constants.MIN_WEAPON_STRENGTH, Constants.MAX_WEAPON_STRENGTH);
    }

    public Item() {
        _pos = new Point(0, 0);
        _type = "";
        _subtype = "";
        _health = 0;
        _maximalHealth = 0;
        _dexterity = 0;
        _strength = 0;
        _price = 0;
        _steps = 0;
    }

    public Item(int x, int y) {
        _pos = new Point(x, y);
        _type = Constants.TYPE_ITEMS[UserRandom.getRandom(1, 4)];
        switch (_type) {
            case "Food":
                initFood();
                break;
            case "Elixir":
            case "Scroll":
                initElixirsandScrolls();
                break;
            case "Weapon":
                initWeapon();
                break;
        }
    }

    public Item(int x, int y, int lvl) {
        _pos = new Point(x, y);
        _type = "Treasure";
        initTreasure(lvl);
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

    public String getType() {
        return _type;
    }

    public void setType(String type) {
        _type = type;
    }

    public String getSubtype() {
        return _subtype;
    }

    public void setSubtype(String subtype) {
        _subtype = subtype;
    }

    public int getPrice() {
        return _price;
    }

    public void setPrice(int price) {
        _price = price;
    }

    public int getStrength() {
        return _strength;
    }

    public int getDexterity() {
        return _dexterity;
    }

    public int getSteps() {
        return _steps;
    }

    public void setSteps(int num) {
        _steps = num;
    }

    public void setDexterity(int num) {
        _dexterity = num;
    }

    public void setStrength(int num) {
        _strength = num;
    }

    public void setMaximalHealth(int num) {
        _maximalHealth = num;
    }

    public void setX(int x) {
        _pos.setX(x);
    }

    public void setY(int y) {
        _pos.setY(y);
    }

    public int getHealth() {
        return _health;
    }

    public void setHealth(int health) {
        _health = health;
    }

    public int getMaximalHealth() {
        return _maximalHealth;
    }
}
