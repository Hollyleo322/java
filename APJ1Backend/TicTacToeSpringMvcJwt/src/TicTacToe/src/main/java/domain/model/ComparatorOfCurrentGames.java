package domain.model;

import java.util.Comparator;

public class ComparatorOfCurrentGames implements Comparator<CurrentGame> {

    @Override
    public int compare(CurrentGame o1, CurrentGame o2) {
        int res = 0;
        if (o1.getUuid() < o2.getUuid()) {
            res =  -1;
        }
        else if (o1.getUuid() > o2.getUuid()) {
            res = 1;
        }
        return res;
    }
}
