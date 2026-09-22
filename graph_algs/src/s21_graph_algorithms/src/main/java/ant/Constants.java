package ant;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum Constants {
  ALPHA(1.),
  BETA(1.),
  P(0.2);

  private final double value;

  Constants(double value) {
    this.value = value;
  }

}
