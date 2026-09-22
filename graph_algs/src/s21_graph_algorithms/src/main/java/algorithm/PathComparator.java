package algorithm;

import java.util.Comparator;

public class PathComparator implements Comparator<Path> {

  @Override
  public int compare(Path o1, Path o2) {
    if (o1.getWeight() < o2.getWeight()) {
      return -1;
    } else if (o1.getWeight() == o2.getWeight()) {
      return 0;
    }
    return 1;
  }
}
