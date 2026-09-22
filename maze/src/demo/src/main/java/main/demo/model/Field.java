package main.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Field {

  private int[][] vertical;
  private int[][] horizontal;
  private int rows;
  private int cols;
}
