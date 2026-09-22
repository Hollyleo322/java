package domain;

public class Counter {
    private int _countTreasures = 0;
    private int _destroyedEnemies = 0;
    private int _eatedFood = 0;
    private int _drinkedElixirs = 0;
    private int _readedScrolls = 0;
    private int _countHits = 0;
    private int _countMisses = 0;
    private int _countSteps = 0;

    public void incremetHits() {
        _countHits++;
    }

    public void incremetMisses() {
        _countMisses++;
    }

    public int getCountTreasures() {
        return _countTreasures;
    }

    public void setCountTreasures(int countTreasures) {
        _countTreasures = countTreasures;
    }

    public int getDestroyedEnemies() {
        return _destroyedEnemies;
    }

    public void setDestroyedEnemies(int destroyedEnemies) {
        _destroyedEnemies = destroyedEnemies;
    }

    public void incrementDestroyedEnemies() {
        _destroyedEnemies++;
    }

    public int getEatedFood() {
        return _eatedFood;
    }

    public void setEatedFood(int eatedFood) {
        _eatedFood = eatedFood;
    }

    public void incrementEatedFood() {
        _eatedFood++;
    }

    public int getDrinkedElixirs() {
        return _drinkedElixirs;
    }

    public void setDrinkedElixirs(int drinkedElixirs) {
        _drinkedElixirs = drinkedElixirs;
    }

    public void incrementDrinkedElixirs() {
        _drinkedElixirs++;
    }

    public int getReadedScrolls() {
        return _readedScrolls;
    }

    public void setReadedScrolls(int readedScrolls) {
        _readedScrolls = readedScrolls;
    }

    public void incrementReadedScrolls() {
        _readedScrolls++;
    }

    public int getCountHits() {
        return _countHits;
    }

    public void setCountHits(int countHits) {
        _countHits = countHits;
    }

    public int getCountMisses() {
        return _countMisses;
    }

    public void setCountMisses(int countMisses) {
        _countMisses = countMisses;
    }

    public int getCountSteps() {
        return _countSteps;
    }

    public void setCountSteps(int countSteps) {
        _countSteps = countSteps;
    }

    public void incrementCountSteps() {
        _countSteps++;
    }
}
