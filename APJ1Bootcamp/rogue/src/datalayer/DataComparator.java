package datalayer;

import java.util.Comparator;

public class DataComparator implements Comparator<Data> {
    @Override
    public int compare(Data a, Data b) {
        int res = 0;
        if (a.getCountTreasures() > b.getCountTreasures()) {
            res = -1;
        } else if (a.getCountTreasures() < b.getCountTreasures()) {
            res = 1;
        }
        return res;
    }

}
