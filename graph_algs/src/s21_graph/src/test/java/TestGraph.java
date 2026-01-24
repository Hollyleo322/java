import exception.GraphIsNull;
import exception.MatrixIsNotLoadedException;
import graph.Graph;
import java.io.File;
import java.io.FileNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
public class TestGraph {
  private static Graph graph;
  @BeforeAll
  public static void init() {
    graph = new Graph();
  }
  @Test
  public void testLoad() {
    try {
      graph.loadGraphFromFile("src/graph/test4Graph.txt");
      Assertions.assertEquals( 4, graph.getMatrix().length);
      Assertions.assertTrue(graph.isDirected());
      Assertions.assertFalse(graph.isWeighed());
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
  }
  @Test
  public void testFileNotFoundException() {
    Assertions.assertThrows(FileNotFoundException.class, () -> {
      graph.loadGraphFromFile("notexisted.txt");
    });
  }
  @Test
  public void testOneVertex() {
    try {
      graph.loadGraphFromFile("src/graph/test1Vertic.txt");
      Assertions.assertNull(graph.getMatrix());
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
  }
  @Test
  public void testNegativeWeight() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> graph.loadGraphFromFile("src/graph/testNegativeWeight.txt"));
  }

  @Test
  public void testSaveDotFile() {
    try {
      graph.setDirected(false);
      graph.setWeighed(false);
      graph.loadGraphFromFile("src/graph/test.txt");
      graph.exportGraphToDot("testResult");
      File result = new File("dot/testResult.dot");
      Assertions.assertTrue(result.exists());
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
  }
  @Test
  public void testSaveDirDotFile() {
    try {
      graph.setDirected(false);
      graph.setWeighed(false);
      graph.loadGraphFromFile("src/graph/testDirected.txt");
      graph.exportGraphToDot("testDirResult");
      File result = new File("dot/testDirResult.dot");
      Assertions.assertTrue(result.exists());
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
  }
  @Test
  public void testSaveDotFileWithNull() {
    graph.setMatrix(null);
    Assertions.assertThrows(MatrixIsNotLoadedException.class, () -> graph.exportGraphToDot("testException"));
  }
  @Test
  public void testNullable() {
    Assertions.assertThrows(GraphIsNull.class, () -> graph.loadGraphFromFile("src/graph/testNullable.txt"));
  }
}
