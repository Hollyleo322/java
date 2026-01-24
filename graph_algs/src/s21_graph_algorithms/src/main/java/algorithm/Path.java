package algorithm;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Path {

  private final int indexFrom;
  private final int indexTo;
  private final int weight;
}
