import algorithm.GraphAlgorithms;
import exception.IncorrectGraph;
import exception.IncorrectVertex;
import exception.MatrixIsNotLoadedException;
import graph.Graph;
import java.io.FileNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestGraphAlgs {

  private static Graph graph;
  private static GraphAlgorithms graphAlgorithms;

  @BeforeAll
  public static void init() {
    graph = new Graph();
    graphAlgorithms = new GraphAlgorithms();
  }

  @Test
  public void testDfs() {
    graph.setMatrix(null);
    Assertions.assertThrows(MatrixIsNotLoadedException.class,
        () -> graphAlgorithms.depthFirstSearch(graph, 322));
    try {
      graph.loadGraphFromFile("src/graph/testDfs.txt");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    int[] expected = {1, 9, 10, 5, 8, 6, 7, 2, 3, 4};
    Assertions.assertArrayEquals(expected, graphAlgorithms.depthFirstSearch(graph, 1));
    Assertions.assertThrows(IncorrectVertex.class,
        () -> graphAlgorithms.depthFirstSearch(graph, 322));
  }

  @Test
  public void testBfs() {
    graph.setMatrix(null);
    Assertions.assertThrows(MatrixIsNotLoadedException.class,
        () -> graphAlgorithms.breadthFirstSearch(graph, 322));
    try {
      graph.loadGraphFromFile("src/graph/testDfs.txt");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    int[] expected = {1, 2, 5, 9, 3, 6, 8, 10, 4, 7};
    Assertions.assertArrayEquals(expected, graphAlgorithms.breadthFirstSearch(graph, 1));
    Assertions.assertThrows(IncorrectVertex.class,
        () -> graphAlgorithms.breadthFirstSearch(graph, 322));
  }

  @Test
  public void testDextra() {
    graph.setMatrix(null);
    Assertions.assertThrows(MatrixIsNotLoadedException.class,
        () -> graphAlgorithms.getShortestPathBetweenVertices(graph, 1, 5));
    try {
      graph.loadGraphFromFile("src/graph/testDirected.txt");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    Assertions.assertThrows(IncorrectGraph.class, () -> graphAlgorithms.getShortestPathBetweenVertices(graph, 1, 2));
    try {
      graph.loadGraphFromFile("src/graph/testDeixtra.txt");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    Assertions.assertEquals(11, graphAlgorithms.getShortestPathBetweenVertices(graph, 1, 6));
    Assertions.assertEquals(20, graphAlgorithms.getShortestPathBetweenVertices(graph, 1, 5));
    Assertions.assertEquals(20, graphAlgorithms.getShortestPathBetweenVertices(graph, 1, 4));
    Assertions.assertEquals(9, graphAlgorithms.getShortestPathBetweenVertices(graph, 1, 3));
    Assertions.assertEquals(7, graphAlgorithms.getShortestPathBetweenVertices(graph, 1, 2));
    Assertions.assertThrows(IncorrectVertex.class,
        () -> graphAlgorithms.getShortestPathBetweenVertices(graph, 0, 2));
    Assertions.assertThrows(IncorrectVertex.class,
        () -> graphAlgorithms.getShortestPathBetweenVertices(graph, 1, 0));
  }

  @Test
  public void testFloydWarshall() {
    graph.setMatrix(null);
    Assertions.assertThrows(MatrixIsNotLoadedException.class,
        () -> graphAlgorithms.getShortestPathsBetweenAllVertices(graph));
    try {
      graph.loadGraphFromFile("src/graph/testDirected.txt");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    Assertions.assertThrows(IncorrectGraph.class, () -> graphAlgorithms.getShortestPathsBetweenAllVertices(graph));
    try {
      graph.loadGraphFromFile("src/graph/testFloydWarshall.txt");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    int[][] actual = graphAlgorithms.getShortestPathsBetweenAllVertices(graph);
    int[][] expected = {{0, 5, 10, 8},
                        {5, 0, 5, 3},
                        {3, 8, 0, 11},
                        {2, 7, 5, 0}};
    for (int i = 0; i < actual.length; i++) {
      Assertions.assertArrayEquals(expected[i], actual[i]);

    }
  }

  @Test
  public void testPrima() {
    graph.setMatrix(null);
    Assertions.assertThrows(MatrixIsNotLoadedException.class,
        () -> graphAlgorithms.getLeastSpanningTree(graph));
    try {
      graph.loadGraphFromFile("src/graph/testDirected.txt");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    Assertions.assertThrows(IncorrectGraph.class, () -> graphAlgorithms.getLeastSpanningTree(graph));
    try {
      graph.loadGraphFromFile("src/graph/testPrima.txt");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    int[][] expected = {{0, 0, 1, 0, 0, 0},
                        {0, 0, 0, 0, 3, 0},
                        {0, 5, 0, 0, 0, 4},
                        {0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 2, 0, 0}
    };
    int[][] actual = graphAlgorithms.getLeastSpanningTree(graph);
    for (int i = 0; i < actual.length; i++) {
      Assertions.assertArrayEquals(expected[i], actual[i]);
    }
  }
  @Test
  public void testAntAlg() {
    graph.setMatrix(null);
    Assertions.assertThrows(MatrixIsNotLoadedException.class, () -> graphAlgorithms.solveTravelingSalesmanProblem(graph));
    try {
      graph.loadGraphFromFile("src/graph/testDirected.txt");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    Assertions.assertThrows(IncorrectGraph.class, () -> graphAlgorithms.solveTravelingSalesmanProblem(graph));
    try {
      graph.loadGraphFromFile("src/graph/testAntAlg.txt");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    Assertions.assertEquals(5, graphAlgorithms.solveTravelingSalesmanProblem(graph).getVertices().length);
  }
}

