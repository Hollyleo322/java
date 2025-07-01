package domain;

import java.util.Vector;

public class Backpack {
    private int _quantityTreasure = 0; // количество сокровищ в рюкзаке
    private Vector<Item> _foods;
    private Vector<Item> _elixirs;
    private Vector<Item> _scrolls;
    private Vector<Item> _weapons;

    public Backpack() {
        _foods = new Vector<>();
        _elixirs = new Vector<>();
        _scrolls = new Vector<>();
        _weapons = new Vector<>();
    }

    public void clear() {
        _quantityTreasure = 0;
        _foods.clear();
        _elixirs.clear();
        _scrolls.clear();
        _weapons.clear();
    }

    public void addItem(Item item, Counter cnt) {
        switch (item.getType()) {
            case "Treasure":
                _quantityTreasure += item.getPrice();
                cnt.setCountTreasures(cnt.getCountTreasures() + item.getPrice());
                break;
            case "Food":
                _foods.add(item);
                break;
            case "Elixir":
                _elixirs.add(item);
                break;
            case "Scroll":
                _scrolls.add(item);
                break;
            case "Weapon":
                _weapons.add(item);
                break;
        }
    }

    public int getQuantityTreasure() {
        return _quantityTreasure;
    }

    public void setQuantityTreasure(int quantityTreasure) {
        _quantityTreasure = quantityTreasure;
    }

    public Vector<Item> getFoods() {
        return _foods;
    }

    public Vector<Item> getElixirs() {
        return _elixirs;
    }

    public Vector<Item> getScrolls() {
        return _scrolls;
    }

    public Vector<Item> getWeapons() {
        return _weapons;
    }

    public boolean isFull(Item item) {
        boolean res = false;
        switch (item.getType()) {
            case "Food":
                if (getFoods().size() >= 9) {
                    res = true;
                }
                break;
            case "Weapon":
                if (getWeapons().size() >= 9) {
                    res = true;
                }
                break;
            case "Elixir":
                if (getElixirs().size() >= 9) {
                    res = true;
                }
                break;
            case "Scroll":
                if (getScrolls().size() >= 9) {
                    res = true;
                }
                break;
        }
        return res;
    }
}
