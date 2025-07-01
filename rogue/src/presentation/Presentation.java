package presentation;

import domain.Constants;

import java.security.PublicKey;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import domain.Backpack;
import domain.Character;
import jcurses.system.CharColor;
import jcurses.system.Toolkit;
import jcurses.system.InputChar;
import jcurses.util.*;
import domain.Room;
import domain.Constants;
import domain.Corridor;
import domain.Enemy;
import domain.Graph;
import domain.Point;
import domain.Item;

public class Presentation {
    public static void outputRooms(Room[] rms, boolean inEntrance) {
        for (int i = 0; i < Constants.ROOMS_NUM; i++) {
            if (!rms[i].getisVisible() && !inEntrance) {
                continue;
            }
            for (int j = rms[i].getY() + 1; j < rms[i].getY() + rms[i].getHeight() - 1; j++) {
                for (int k = rms[i].getX() + 1; k < rms[i].getX() + rms[i].getWidth() - 1; k++) {
                    Toolkit.printString(".", k, j, new CharColor(CharColor.BLACK, CharColor.WHITE));
                }
            }
        }
        outputWalls(rms);
    }

    public static void outputWalls(Room[] rms) {
        for (int i = 0; i < Constants.ROOMS_NUM; i++) {
            if (rms[i].getisVisited()) {
                drawVerticalWall(rms[i].getX(), rms[i].getY(), rms[i].getHeight());
                drawVerticalWall(rms[i].getX() + rms[i].getWidth() - 1, rms[i].getY(), rms[i].getHeight());
                drawHorizontalWall(rms[i].getX(), rms[i].getY(), rms[i].getWidth());
                drawHorizontalWall(rms[i].getX(), rms[i].getY() + rms[i].getHeight() - 1, rms[i].getWidth());
            }
        }
    }

    public static void drawHorizontalWall(int startX, int startY, int width) {
        for (int i = startX; i < startX + width; i++) {
            Toolkit.printString("#", i, startY, new CharColor(CharColor.BLACK, CharColor.WHITE));
        }
    }

    public static void drawVerticalWall(int startX, int startY, int height) {
        for (int i = startY; i < startY + height; i++) {
            Toolkit.printString("#", startX, i, new CharColor(CharColor.BLACK, CharColor.WHITE));
        }
    }

    public static void drawCorridors(Corridor[] corridors) {
        for (int i = 0; i < corridors.length; i++) {
            for (int j = 0; j < corridors[i].getPoints().size(); j++) {
                if (corridors[i].getUnveiled().contains(j)) {
                    Toolkit.printString("z", corridors[i].getPoints().get(j).getX(),
                            corridors[i].getPoints().get(j).getY(), new CharColor(CharColor.BLACK, CharColor.WHITE));
                }
            }
        }
    }

    public static void outputExit(Point exit) {
        Toolkit.printString("E", exit.getX(), exit.getY(), new CharColor(CharColor.BLACK, CharColor.GREEN));
    }

    public static void outputEntrances(Room[] rms) {
        for (int i = 0; i < Constants.ROOMS_NUM; i++) {
            for (Point it : rms[i].getVectorEntrances()) {
                if (rms[i].getisVisited()) {
                    Toolkit.printString("+", it.getX(), it.getY(), new CharColor(CharColor.BLACK, CharColor.WHITE));
                }

            }
        }
    }

    public static StartData outputStartMenu() {
        Toolkit.printString("S21_Rogue-:-The\nadventure\ngame", Toolkit.getScreenWidth() / 2 - 20, 1,
                new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString("Authors-:-diablory,gwynesst", Toolkit.getScreenWidth() / 2 - 20, 3,
                new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString("Rogue's\nNAME:-", Toolkit.getScreenWidth() / 2 - 20, 5,
                new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString("Press\nenter\nto\nstart", Toolkit.getScreenWidth() / 2 - 20, 7,
                new CharColor(CharColor.BLACK, CharColor.WHITE));
        int counter = 15;
        StringBuilder sb = new StringBuilder();
        while (true) {
            InputChar in = Toolkit.readCharacter();
            if (in.getCode() == InputChar.KEY_BACKSPACE) {
                if (sb.length() > 0) {
                    Toolkit.printString("_", Toolkit.getScreenWidth() / 2 - 20 + counter - 1, 5,
                            new CharColor(CharColor.BLACK, CharColor.BLACK));
                    sb.deleteCharAt(sb.length() - 1);
                }
                counter--;
                if (counter < 15) {
                    counter = 15;
                }
            } else {
                if (counter < 25) {
                    Toolkit.printString(in.toString(), Toolkit.getScreenWidth() / 2 - 20 + counter, 5,
                            new CharColor(CharColor.BLACK, CharColor.RED));
                    sb.append(in);
                    counter++;
                }
                if (in.getCharacter() == '\n') {
                    break;
                }
            }

        }
        boolean isLoad = chooseStage();
        clear();
        return new StartData(sb.toString(), isLoad);
    }

    public static boolean chooseStage() {
        boolean res = false;
        CharColor currentStage = new CharColor(CharColor.BLACK, CharColor.GREEN);
        String[] stages = { "Start\nnew\ngame", "Load\nlast\nsaved\ngame", "Settings\nof\ninput" };
        int index = 0;
        boolean flag = true;
        while (flag) {
            for (int i = 0, j = 9; i < stages.length; i++, j += 2) {
                Toolkit.printString(stages[i], Toolkit.getScreenWidth() / 2 - 20, j,
                        i == index ? currentStage : new CharColor(CharColor.BLACK, CharColor.WHITE));
            }
            int in = Toolkit.readCharacter().getCode();
            switch (in) {
                case 'W':
                case 'w':
                    index--;
                    if (index == -1) {
                        index = 0;
                    }
                    break;
                case 's':
                case 'S':
                    index++;
                    if (index == 3) {
                        index = 2;
                    }
                    break;
                case '\n':
                    if (index == 0) {
                        flag = false;
                    } else if (index == 1) {
                        flag = false;
                        res = true;
                    } else if (index == 2) {
                        inputInfo();
                    }
                    break;
            }
        }
        return res;
    }

    public static void inputInfo() {
        int index = 0;
        int middle = Toolkit.getScreenHeight() / 2;
        Toolkit.printString("Move\nup", Toolkit.getScreenWidth() / 2 - 40, 15,
                new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString("W", Toolkit.getScreenWidth() / 2 - 15, 15,
                new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString("Move\ndown", Toolkit.getScreenWidth() / 2 - 40, 17,
                new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString("S", Toolkit.getScreenWidth() / 2 - 15, 17,
                new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString("Move\nleft", Toolkit.getScreenWidth() / 2 - 40, 19,
                new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString("A", Toolkit.getScreenWidth() / 2 - 15, 19,
                new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString("Move\nright", Toolkit.getScreenWidth() / 2 - 40, 21,
                new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString("D", Toolkit.getScreenWidth() / 2 - 15, 21,
                new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString("Use\nweapon\nfrom\nbackpack", Toolkit.getScreenWidth() / 2, 15,
                new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString("H", Toolkit.getScreenWidth() / 2 + 30, 15,
                new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString("Use\nfood\nfrom\nbackpack", Toolkit.getScreenWidth() / 2, 17,
                new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString("J", Toolkit.getScreenWidth() / 2 + 30, 17,
                new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString("Use\nelixir\nfrom\nbackpack", Toolkit.getScreenWidth() / 2, 19,
                new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString("K", Toolkit.getScreenWidth() / 2 + 30, 19,
                new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString("Use\nscroll\nfrom\nbackpack", Toolkit.getScreenWidth() / 2, 21,
                new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString("E", Toolkit.getScreenWidth() / 2 + 30, 21,
                new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString("Show\nstats", Toolkit.getScreenWidth() / 2 - 40, 23,
                new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString("Z", Toolkit.getScreenWidth() / 2 - 15, 23,
                new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString("Save\ngame", Toolkit.getScreenWidth() / 2, 23,
                new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString("X", Toolkit.getScreenWidth() / 2 + 30, 23,
                new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString("Exit", Toolkit.getScreenWidth() / 2, 25,
                new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString("Q", Toolkit.getScreenWidth() / 2 + 30, 25,
                new CharColor(CharColor.BLACK, CharColor.WHITE));
    }

    public static void outputPlayer(Point player) {
        Toolkit.printString("@", player.getX(), player.getY(), new CharColor(CharColor.BLACK, CharColor.WHITE));
    }

    public static void outputEnemies(Room[] rms, boolean inIntrance) {
        for (int i = 0; i < Constants.ROOMS_NUM; i++) {
            for (Enemy it : rms[i].getEnemies()) {
                Toolkit.printString(it.toString(), it.getX(), it.getY(),
                        new CharColor(CharColor.BLACK, it.getColor()));
            }
        }
    }

    public static void outputItems(Room[] rms) {
        for (int i = 0; i < Constants.ROOMS_NUM; i++) {
            for (Item it : rms[i].getItems()) {
                String symbolItem = getSymbol(it);
                Toolkit.printString(symbolItem, it.getX(), it.getY(), new CharColor(CharColor.BLACK, CharColor.CYAN));
            }
        }
    }

    public static int outputBackpackItems(Vector<Item> backpack_items) {
        // Toolkit.clearScreen(new CharColor(CharColor.BLACK, CharColor.WHITE));
        clear();
        int index = 0;
        if (!backpack_items.isEmpty()) {
            if (backpack_items.get(0).getType().equals("Weapon")) {
                Toolkit.printString("0)\nTake\noff\nweapon", 1, 3, new CharColor(CharColor.BLACK, CharColor.WHITE));
            }
        } else {
            Toolkit.printString("Backpack\nis\nempty", 1, 3, new CharColor(CharColor.BLACK, CharColor.WHITE));
        }
        for (int i = 0, j = 2; i < backpack_items.size(); i++, j += 2) {
            Toolkit.printString(
                    (i + 1) + ")_" + backpack_items.get(i).getType() + "_" + backpack_items.get(i).getSubtype(),
                    1, 3 + j, new CharColor(CharColor.BLACK, CharColor.WHITE));
        }
        Toolkit.printString("Press\nany\nkey\nto\nexit", 1, 23, new CharColor(CharColor.BLACK, CharColor.WHITE));
        while (true) {
            index = getIndex();
            break;
        }
        // Toolkit.clearScreen(new CharColor(CharColor.BLACK, CharColor.WHITE));
        clear();
        return index;
    }

    public static void outputStatistics(Vector<String> info) {
        clear();
        int index = 0;
        Toolkit.printString("STATS", Toolkit.getScreenWidth() / 2, 0,
                new CharColor(CharColor.BLACK, CharColor.WHITE));
        for (int i = 0, j = 2; i < info.size(); i++, j += 2) {
            Toolkit.printString(info.get(i), 7, j,
                    new CharColor(CharColor.BLACK, CharColor.WHITE));
        }
        Toolkit.printString("Press\nany\nkey\nto\nexit", 1, Toolkit.getScreenHeight() / 2 + 15,
                new CharColor(CharColor.BLACK, CharColor.WHITE));
        while (true) {
            index = getIndex();
            break;
        }
        clear();
    }

    public static String getSymbol(Item it) {
        StringBuilder sb = new StringBuilder();
        if (it.getType().equals("Weapon")) {
            sb.append('W');
        } else if (it.getType().equals("Food")) {
            sb.append('F');
        } else if (it.getType().equals("Elixir")) {
            sb.append('E');
        } else if (it.getType().equals("Scroll")) {
            sb.append('S');
        } else if (it.getType().equals("Treasure")) {
            sb.append('T');
        }
        return sb.toString();
    }

    public static void clear() {
        for (int i = 0; i < Toolkit.getScreenHeight(); i++) {
            for (int j = 0; j < Toolkit.getScreenWidth(); j++) {
                Toolkit.printString("_", j, i, new CharColor(CharColor.BLACK, CharColor.BLACK));
            }
        }
    }

    public static void outputСharacteristics(Controller cnt) {
        Vector<String> characteristics = cnt.getPlayerInfo();
        for (int i = 0, j = 15; i < characteristics.size(); i++, j += 15) {
            Toolkit.printString(characteristics.get(i), 1 + j, Toolkit.getScreenHeight() - 1,
                    new CharColor(CharColor.BLACK, CharColor.YELLOW));
        }
    }

    public static int getIndex() {
        int key = Toolkit.readCharacter().getCode();
        int index = -2;
        switch (key) {
            case '0':
                index = -1;
                break;
            case '1':
                index = 0;
                break;
            case '2':
                index = 1;
                break;
            case '3':
                index = 2;
                break;
            case '4':
                index = 3;
                break;
            case '5':
                index = 4;
                break;
            case '6':
                index = 5;
                break;
            case '7':
                index = 6;
                break;
            case '8':
                index = 7;
                break;
            case '9':
                index = 8;
                break;
            default:
                break;
        }
        return index;
    }

    public static void outputElixirsinfo(Controller cnt) {
        for (int j = 0; j < Toolkit.getScreenWidth(); j++) {
            Toolkit.printString("_", j, Toolkit.getScreenHeight() - 2, new CharColor(CharColor.BLACK, CharColor.BLACK));
        }
        Vector<String> info = cnt.getElixirsInfo();
        for (int i = 0, j = 0; i < info.size(); i++, j += 20) {
            Toolkit.printString(info.get(i), j, Toolkit.getScreenHeight() - 2,
                    new CharColor(CharColor.BLACK, CharColor.RED));
        }
    }

    public static void fog(Room[] rms, boolean inEntrance) {
        for (int i = 0; i < Constants.ROOMS_NUM; i++) {
            if (rms[i].getisVisible() && !inEntrance) {
                continue;
            }
            for (int j = rms[i].getY() + 1; j < rms[i].getY() + rms[i].getHeight() - 1; j++) {
                for (int k = rms[i].getX() + 1; k < rms[i].getX() + rms[i].getWidth() - 1; k++) {
                    if (!rms[i].isUnveiled(k, j)) {
                        Toolkit.printString("_", k, j, new CharColor(CharColor.BLACK, CharColor.BLACK));
                    }
                }
            }
            for (Point it : rms[i].getVectorEntrances()) {
                if (!rms[i].getisVisible()) {
                    Toolkit.printString("_", it.getX(), it.getY(), new CharColor(CharColor.BLACK, CharColor.BLACK));
                }
            }
        }
    }

    public static void fogCorridor(Corridor[] corridors) {
        for (int i = 0; i < corridors.length; i++) {
            for (int j = 0; j < corridors[i].getPoints().size(); j++) {
                if (!corridors[i].getUnveiled().contains(j)) {
                    Toolkit.printString("_", corridors[i].getPoints().get(j).getX(),
                            corridors[i].getPoints().get(j).getY(), new CharColor(CharColor.BLACK, CharColor.BLACK));
                }
            }
        }
    }

    public static void outputEndofDeath() {
        clear();
        Toolkit.printString("GAME\nOVER!\nYou\nare\ndead", Toolkit.getScreenWidth() / 2 - 20,
                Toolkit.getScreenHeight() / 2,
                new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString("Press\nenter\nto\nexit", 1, Toolkit.getScreenHeight() / 2 + 15,
                new CharColor(CharColor.BLACK, CharColor.WHITE));
        while (true) {
            int key = Toolkit.readCharacter().getCode();
            if (key == '\n') {
                break;
            }
        }
    }

    public static void outputEndForWinner() {
        clear();
        Toolkit.printString("Congratulations,\nyou\nwin!", Toolkit.getScreenWidth() / 2 - 20,
                Toolkit.getScreenHeight() / 2,
                new CharColor(CharColor.BLACK, CharColor.WHITE));
        Toolkit.printString("Press\nenter\nto\nexit", 1, Toolkit.getScreenHeight() / 2 + 15,
                new CharColor(CharColor.BLACK, CharColor.WHITE));
        while (true) {
            int key = Toolkit.readCharacter().getCode();
            if (key == '\n') {
                break;
            }
        }
    }

    public static void outputFightLog(String log) {
        Toolkit.printString("CLEARCLEARCLEARCLEARCLEARCLEARCLEARCLEARCLEARCLEARCLEARCLEARCLEARCLEAR",
                0, 0, new CharColor(CharColor.BLACK, CharColor.BLACK));
        Toolkit.printString(log, 0, 0, new CharColor(CharColor.BLACK, CharColor.WHITE));
    }

}