package algorithm;

import ant.AntData;
import ant.AntTableBuilder;
import ant.Border;
import ant.Constants;
import ant.Formula;
import exception.IncorrectGraph;
import exception.IncorrectVertex;
import exception.MatrixIsNotLoadedException;
import exception.NoWay;
import graph.Graph;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Random;
import structure.Queue.PointerQueue;
import structure.Stack.PointerStack;

public class GraphAlgorithms {

  private final PointerQueue pointerQueue = new PointerQueue();
  private final PointerStack pointerStack = new PointerStack();

  public int[] depthFirstSearch(Graph graph, int startVertex) {
    if (graph.getMatrix() == null) {
      throw new MatrixIsNotLoadedException("Matrix is not loaded");
    }
    if (startVertex < 1 || startVertex >= 1 + graph.getMatrix().length) {
      throw new IncorrectVertex("Incorrect index of vertex");
    }
    List<Integer> visited = new ArrayList<>();
    int current = startVertex - 1;
    pointerStack.push(current);
    while (!pointerStack.empty()) {
      if (!visited.contains(current)) {
        visited.add(current);
        for (int j = 0; j < graph.getMatrix().length; j++) {
          if (graph.getMatrix()[(int) current][j] != 0 && !visited.contains(j)) {
            pointerStack.push(j);
          }
        }
      }
      current = (int) pointerStack.pop();
    }
    int[] result = new int[visited.size()];
    visited.forEach((vertex) -> result[visited.indexOf(vertex)] = vertex + 1);
    return result;
  }

  public int[] breadthFirstSearch(Graph graph, int startVertex) {
    if (graph.getMatrix() == null) {
      throw new MatrixIsNotLoadedException("Matrix is not loaded");
    }
    if (startVertex < 1 || startVertex >= 1 + graph.getMatrix().length) {
      throw new IncorrectVertex("Incorrect index of vertex");
    }
    List<Integer> visited = new ArrayList<>();
    pointerQueue.push(startVertex - 1);
    visited.add(startVertex - 1);
    while (!pointerQueue.empty()) {
      int current = (int) pointerQueue.pop();
      for (int j = 0; j < graph.getMatrix().length; j++) {
        if (graph.getMatrix()[current][j] != 0 && !visited.contains(j)) {
          pointerQueue.push(j);
          visited.add(j);
        }
      }
    }
    int[] result = new int[visited.size()];
    visited.forEach((vertex) -> result[visited.indexOf(vertex)] = vertex + 1);
    return result;
  }

  public int getShortestPathBetweenVertices(Graph graph, int vertex1, int vertex2) {
    if (graph.getMatrix() == null) {
      throw new MatrixIsNotLoadedException("Matrix is not loaded");
    }
    if (vertex1 < 1 || vertex1 >= 1 + graph.getMatrix().length) {
      throw new IncorrectVertex("Incorrect index of vertex 1");
    }
    if (vertex2 < 1 || vertex2 >= 1 + graph.getMatrix().length) {
      throw new IncorrectVertex("Incorrect index of vertex 2");
    }
    if (!isConnected(graph)) {
      throw new IncorrectGraph("Not connected graph");
    }
    List<Integer> visited = new ArrayList<>();
    List<Integer> labels = new ArrayList<>();
    initVertexes(graph.getMatrix(), vertex1 - 1, labels);
    while (visited.size() != graph.getMatrix().length) {
      int current = getNearest(visited, labels);
      setLabels(graph.getMatrix(), current, labels);
      visited.add(current);
    }
    return labels.get(vertex2 - 1);
  }

  private int getNearest(List<Integer> visited, List<Integer> labels) {
    int nearest = -1;
    Integer min = Integer.MAX_VALUE;
    for (int i = 0; i < labels.size(); i++) {
      if (min > labels.get(i) && !visited.contains(i)) {
        nearest = i;
        min = labels.get(i);
      }
    }
    return nearest;
  }

  private void setLabels(int[][] matrix, int current, List<Integer> labels) {
    for (int i = 0; i < matrix.length; i++) {
      if (matrix[current][i] != 0) {
        if (labels.get(current) + matrix[current][i] < labels.get(i)) {
          labels.set(i, labels.get(current) + matrix[current][i]);
        }
      }
    }
  }

  private void initVertexes(int[][] matrix, int startedVertex, List<Integer> labels) {
    for (int i = 0; i < matrix.length; i++) {
      if (i == startedVertex) {
        labels.add(i, 0);
      } else {
        labels.add(i, Integer.MAX_VALUE);
      }
    }
  }

  public int[][] getShortestPathsBetweenAllVertices(Graph graph) {
    if (graph.getMatrix() == null) {
      throw new MatrixIsNotLoadedException("Matrix is not loaded");
    }
    if (!isConnected(graph)) {
      throw new IncorrectGraph("Not connected graph");
    }
    int[][] dist = new int[graph.getMatrix().length][graph.getMatrix().length];
    for (int i = 0; i < graph.getMatrix().length; i++) {
      for (int j = 0; j < graph.getMatrix().length; j++) {
        if (i == j) {
          dist[i][j] = 0;
        } else if (graph.getMatrix()[i][j] == 0) {
          dist[i][j] = Integer.MAX_VALUE;
        } else {
          dist[i][j] = graph.getMatrix()[i][j];
        }
      }
    }
    for (int k = 0; k < dist.length; k++) {
      for (int i = 0; i < dist.length; i++) {
        for (int j = 0; j < dist.length; j++) {
          int sum = checkSum(dist[i][k], dist[k][j]);
          dist[i][j] = Integer.min(dist[i][j], sum);
        }
      }
    }
    return dist;
  }

  private int checkSum(int first, int second) {
    if (first == Integer.MAX_VALUE || second == Integer.MAX_VALUE) {
      return Integer.MAX_VALUE;
    }
    return first + second;
  }

  public int[][] getLeastSpanningTree(Graph graph) {
    if (graph.getMatrix() == null) {
      throw new MatrixIsNotLoadedException("Matrix is not loaded");
    }
    if (!isConnected(graph)) {
      throw new IncorrectGraph("Not connected graph");
    }
    PriorityQueue<Path> priorityQueue = new PriorityQueue<>(new PathComparator());
    int[][] result = new int[graph.getMatrix().length][graph.getMatrix().length];
    List<Integer> visited = new LinkedList<>();
    visited.add(0);
    while (visited.size() != graph.getMatrix().length) {
      visited.forEach((index) -> {
        for (int i = 0; i < graph.getMatrix().length; i++) {
          if (graph.getMatrix()[index][i] != 0) {
            priorityQueue.add(new Path(index, i, graph.getMatrix()[index][i]));
          }
        }
      });
      Path least;
      do {
        least = priorityQueue.poll();
      } while (least != null && visited.contains(least.getIndexTo()));
      if (least != null) {
        result[least.getIndexFrom()][least.getIndexTo()] = least.getWeight();
        priorityQueue.clear();
        visited.add(least.getIndexTo());
      }
    }
    return result;
  }

  public TsmResult solveTravelingSalesmanProblem(Graph graph) {
    if (graph.getMatrix() == null) {
      throw new MatrixIsNotLoadedException("Matrix is not loaded");
    }
    if (!isConnected(graph)) {
      throw new IncorrectGraph("Not connected graph");
    }
    AntData[][] antTable = AntTableBuilder.getAntTable(graph);
    Random rnd = new Random();
    List<Integer> visited = new ArrayList<>();
    int distance = 0, to;
    TsmResult result = new TsmResult(new int[antTable.length], 0.);
    for (int i = 0; i < 100; i++) {
      int startedVertex = rnd.nextInt(graph.getMatrix().length);
      int currentVertex = startedVertex;
      distance = 0;
      for (int j = 0; j < antTable.length; j++) {
        if (j == antTable.length - 1) {
          to = startedVertex;
          if (antTable[currentVertex][to].getPath() == 0) {
            throw new IncorrectGraph("Impossible to get back in the started point");
          }
        } else {
          to = getDirection(currentVertex, antTable, visited);
        }
        if (to == -1) {
          throw new IncorrectVertex("Error in the getDirection method");
        }
        distance += antTable[currentVertex][to].getPath();
        visited.add(currentVertex);
        currentVertex = to;
        result.getVertices()[j] = to + 1;
      }
      updatePheromones(startedVertex, result, distance, antTable);
      visited.clear();
    }
    result.setDistance(distance);
    return result;
  }

  private boolean isConnected(Graph graph) {
    boolean result = true;
    for (int i = 0; i < graph.getMatrix().length && result; i++) {
      if (graph.getMatrix().length != depthFirstSearch(graph, i + 1).length) {
        result = false;
      }
    }
    return result;
  }

  private void updatePheromones(int started, TsmResult path, int distance, AntData[][] antTable) {
    double delta = 100. / distance;
    int from = started;
    int to = 0;
    evaporation(antTable);
    for (int i = 0; i < path.getVertices().length; i++) {
      to = path.getVertices()[i] - 1;
      antTable[from][to].setPheromones(antTable[from][to].getPheromones() + delta);
      from = to;
    }
    antTable[from][to].setPheromones(antTable[from][to].getPheromones() + delta);
  }

  private void evaporation(AntData[][] antTable) {
    for (int i = 0; i < antTable.length; i++) {
      for (int j = 0; j < antTable.length; j++) {
        antTable[i][j].setPheromones((1 - Constants.P.getValue()) * antTable[i][j].getPheromones());
      }
    }
  }

  private int getDirection(int currentVertex, AntData[][] antTable, List<Integer> visited) {
    List<Integer> possible = new LinkedList<>();
    for (int i = 0; i < antTable.length; i++) {
      if (!visited.contains(i) && i != currentVertex && antTable[currentVertex][i].getPath() != 0) {
        possible.add(i);
      }
    }
    if (possible.isEmpty()) {
      throw new NoWay("There is no path from vertex " + currentVertex + " visited = " + visited.toString());
    }
    Map<Integer, Border> chance = new HashMap<>();
    Border current;
    Border previous = null;
    for (Integer i : possible) {
      double probability = Formula.getProbability(antTable, currentVertex, i, possible);
      if (possible.getFirst().equals(i)) {
        current = new Border(0, probability);
        chance.put(i, current);
      } else if (possible.getLast().equals(i)) {
        current = new Border(previous.getRight(), 100);
        chance.put(i, current);
      } else {
        current = new Border(previous.getRight(), previous.getRight() + probability);
        chance.put(i, current);
      }
      previous = current;
    }
    Random rnd = new Random();
    double randomValue = rnd.nextDouble(100.);
    int direction = -1;
    for (Entry<Integer, Border> it : chance.entrySet()) {
      if (randomValue >= it.getValue().getLeft() && randomValue < it.getValue().getRight()) {
        direction = it.getKey();
      }
    }
    return direction;
  }
}