package wrapper.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class WrapperGameState {

  private boolean[][] field;
  private boolean[][] next;
  private Integer score;
  private Integer high_score;
  private Integer lvl;
  private Integer speed;
  private boolean pause;

}
