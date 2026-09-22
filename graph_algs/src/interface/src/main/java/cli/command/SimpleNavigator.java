package cli.command;

import algorithm.GraphAlgorithms;
import graph.Graph;
import java.io.FileNotFoundException;
import java.util.Arrays;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class SimpleNavigator {
  private final Graph graph = new Graph();
  private final GraphAlgorithms graphAlgorithms = new GraphAlgorithms();
  @ShellMethod(key = "load", value = "Load file from specified path")
  public String loadFile(@ShellOption(help = "Absolute path to file with adjacency matrix") String path) {
    String result = "File is loaded";
    try {
      graph.loadGraphFromFile(path);
    } catch (FileNotFoundException e) {
      result = "File isn't found";
    }
    return result;
  }
  @ShellMethod(key = "bfs", value = "breadthFirstSearch")
  public String bfs(@ShellOption(defaultValue = "1", help = "Started vertex") Integer startedVertex){
    String result = "Graph isn't loaded";
    if (graph.getMatrix() != null) {
      result = Arrays.toString(graphAlgorithms.breadthFirstSearch(graph, startedVertex));
    }
    return result;
  }
  @ShellMethod(key = "dfs", value = "depthFirstSearch")
  public String dfs(@ShellOption (defaultValue = "1", help = "Started vertex") Integer startedVertex) {
    String result = "Graph isn't loaded";
    if (graph.getMatrix() != null) {
      result = Arrays.toString(graphAlgorithms.depthFirstSearch(graph, startedVertex));
    }
    return result;
  }
  @ShellMethod(key = "gspbv", value = "getShortestPathBetweenVertices")
  public String gspbv (@ShellOption(defaultValue = "1", help = "Vertex from") Integer startedVertex, @ShellOption(defaultValue = "2", help = "Vertex to") Integer finishedVertex) {
    String result = "Graph isn't loaded";
    if (graph.getMatrix() != null) {
      result = Integer.toString(graphAlgorithms.getShortestPathBetweenVertices(graph, startedVertex, finishedVertex));
    }
    return result;
  }
  @ShellMethod(key = "gspbav", value = "getShortestPathsBetweenAllVertices")
  public void gspbav() {
    if (graph.getMatrix() != null) {
      var matrix = graphAlgorithms.getShortestPathsBetweenAllVertices(graph);
      for (int i = 0; i < matrix.length; i++) {
        System.out.println(Arrays.toString(matrix[i]));
      }
    }
    else {
      System.out.println("Graph isn't loaded");
    }
  }
  @ShellMethod(key = "glst", value = "getLeastSpanningTree")
  public void glst() {
    if (graph.getMatrix() != null) {
      var matrix = graphAlgorithms.getLeastSpanningTree(graph);
      for (int i = 0; i < matrix.length; i++) {
        System.out.println(Arrays.toString(matrix[i]));
      }
    } else {
      System.out.println("Graph isn't loaded");
    }
  }
  @ShellMethod(key = "stsp", value = "solveTravelingSalesmanProblem")
  public String stsp() {
    String result = "Graph isn't loaded";
    if (graph.getMatrix() != null) {
      var tsm = graphAlgorithms.solveTravelingSalesmanProblem(graph);
      result = "Path " + Arrays.toString(tsm.getVertices()) + " distance " + tsm.getDistance();
    }
    return result;
  }
}
