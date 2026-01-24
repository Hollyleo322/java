package ant;

import java.util.List;

public class Formula {

  public static double getProbability(AntData[][] antTable, int i, int j, List<Integer> possible) {
    double teta = 1.0 / antTable[i][j].getPath();
    return 100 * (
        Math.pow(teta, Constants.ALPHA.getValue()) * Math.pow(antTable[i][j].getPheromones(),
            Constants.BETA.getValue()) / sumOfAllVariants(antTable, i, possible));
  }

  private static double sumOfAllVariants(AntData[][] antTable, int from, List<Integer> possible) {
    double sum = 0;
    for (Integer i : possible) {
      double teta = 1.0 / antTable[from][i].getPath();
      sum += (Math.pow(teta, Constants.ALPHA.getValue()) * Math.pow(
          antTable[from][i].getPheromones(), Constants.BETA.getValue()));
    }
    return sum;
  }
}
