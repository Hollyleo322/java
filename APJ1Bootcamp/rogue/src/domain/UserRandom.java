package domain;

import java.util.Random;

/**
 * Класс для получения рандомного числа в диапозоне от min до max
 */
public class UserRandom {
    public static int getRandom(int min, int max) {
        int range = max - min + 1;
        Random rnd = new Random();
        return rnd.nextInt(range) + min;
    }

    public static Direction getRandomDir(int min, int max) {
        int n = getRandom(min, max);
        Direction dir = Direction.Up;
        switch (n) {
            case 0:
                dir = Direction.Up;
                break;
            case 1:
                dir = Direction.Down;
                break;
            case 2:
                dir = Direction.Left;
                break;
            case 3:
                dir = Direction.Right;
                break;
            case 4:
                dir = Direction.UpRight;
                break;
            case 5:
                dir = Direction.UpLeft;
                break;
            case 6:
                dir = Direction.DownRight;
                break;
            case 7:
                dir = Direction.DownLeft;
                break;
        }
        return dir;
    }
}
