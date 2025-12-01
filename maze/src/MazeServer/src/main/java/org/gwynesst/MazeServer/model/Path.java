package org.gwynesst.MazeServer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import main.demo.model.Point;
import java.util.List;

@AllArgsConstructor
@Getter
public class Path {

  private List<Point> path;
  private int rows;
  private int cols;
}
