package ant;

import graph.Graph;

public class AntTableBuilder {

  public static AntData[][] getAntTable(Graph graph) {
    double startedPheromones = 2.;
    AntData[][] result = new AntData[graph.getMatrix().length][graph.getMatrix().length];
    for (int i = 0; i < graph.getMatrix().length; i++) {
      for (int j = 0; j < graph.getMatrix().length; j++) {
        result[i][j] = new AntData();
        result[i][j].setPath(graph.getMatrix()[i][j]);
        result[i][j].setPheromones(startedPheromones);
      }
    }
    return result;
  }
}
