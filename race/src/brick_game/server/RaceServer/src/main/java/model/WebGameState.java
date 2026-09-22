package model;

import constant.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Setter
@Getter
public class WebGameState {

  private boolean[][] field;
  private boolean[][] next;
  private Integer score;
  private Integer high_score;
  private Integer lvl;
  private Integer speed;
  private boolean pause;

  public WebGameState() {
    field = new boolean[Constants.HEIGHT][Constants.WIDTH];
    next = new boolean[Constants.WIDTHFORNEXT][Constants.WIDTHFORNEXT];
    score = 0;
    high_score = 0;
    lvl = 0;
    speed = 0;
    pause = true;
  }
}
