package web.model;

import java.util.Comparator;

public class ComparatorOfStatistic implements Comparator<StatisticWithName> {

    @Override
    public int compare(StatisticWithName o1, StatisticWithName o2) {
        int result = 0;
        if (o1.getRatio() > o2.getRatio()) {
            result =  -1;
        }
        else if (o1.getRatio() < o2.getRatio()) {
            result = 1;
        }
        return result;
    }
}
