package domain;

public class BuilderEntrances {
    public static void buildEntrances(Room first, Room second, int indexFirst, int indexSecond) {
        if (indexSecond - indexFirst == 1) {
            first.makeRightEntrance();
            second.makeLeftEntrance();
        }
        if (indexSecond - indexFirst == 3) {
            first.makeDownEntrance();
            second.makeUpEntrance();
        }
    }
}
