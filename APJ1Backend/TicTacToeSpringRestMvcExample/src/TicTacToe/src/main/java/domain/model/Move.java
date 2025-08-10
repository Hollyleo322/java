package domain.model;

public class Move {
    private int _i;
    private int _j;
    private int _score;

    public Move() {
        _i = 0;
        _j = 0;
        _score = 0;
    }

    public int getI() {
        return _i;
    }

    public int getJ() {
        return _j;
    }

    public int getScore() {
        return _score;

    }

    public void setI(int i) {
        _i = i;
    }

    public void setJ(int j) {
        _j = j;
    }

    public void setScore(int score) {
        _score = score;
    }
}