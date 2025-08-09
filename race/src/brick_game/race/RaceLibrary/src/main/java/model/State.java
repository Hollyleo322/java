package model;

public record State(boolean[][] field, boolean[][] next, int score, int highScore,
                    int level, int speed, boolean pause) {

}
