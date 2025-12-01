import main.demo.model.Cave;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestCave {
  @Test
  public void testInit() {
    int chance = 100;
    int[][] field = {{1,0,1,0}, {1,1,1,1}, {0,1,0,1}, {1,1,1,1}};
    int[][] expected = {{1,0,1,0}, {1,1,1,1}, {0,1,0,1}, {1,1,1,1}};
    Cave cave = new Cave(field,4,4,2,5);
    cave.initWithChance(chance);
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        Assertions.assertEquals(expected[i][j], field[i][j]);
      }
    }
  }
  @Test
  public void testGenNextGen() {
    int limitDeath = 7;
    int limitBrith = 2;
    int[][] field = {{1,0,1,0}, {0,0,0,1}, {0,1,0,0}, {1,0,0,1}};
    int[][] expected = {{0,1,0,1}, {1,1,1,0}, {1,0,1,1}, {0,1,1,1}};
    Cave cave = new Cave(field,4,4,limitBrith, limitDeath);
    cave.generateNextGen();
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        Assertions.assertEquals(expected[i][j], cave.getField()[i][j]);
      }
    }
  }

  @Test
  public void testCountAlive() {
    int limitDeath = 7;
    int limitBrith = 2;
    int[][] field = {{1,0,1,0}, {0,0,0,1}, {0,1,0,0}, {1,0,0,1}};
    Cave cave = new Cave(field,4,4,limitBrith, limitDeath);
    Assertions.assertEquals(5, cave.countAlive(0,0));
  }

  @Test
  public void testCheckLine() {
    int limitDeath = 7;
    int limitBrith = 2;
    int[][] field = {{1,0,1,0}, {0,0,0,1}, {0,1,0,0}, {1,0,0,1}};
    Cave cave = new Cave(field,4,4,limitBrith, limitDeath);
    Assertions.assertEquals(3, cave.checkLine(-1,-1));
  }
  @Test
  public void testCheckInTheMiddle() {
    int limitDeath = 7;
    int limitBrith = 2;
    int[][] field = {{1,0,1,0}, {0,0,0,1}, {0,1,0,0}, {1,0,0,1}};
    Cave cave = new Cave(field,4,4,limitBrith, limitDeath);
    Assertions.assertEquals(1, cave.checkInTheMiddle(0,0));
  }
  @Test
  public void testGetters() {
    int limitDeath = 7;
    int limitBrith = 2;
    int[][] field = {{1,0,1,0}, {0,0,0,1}, {0,1,0,0}, {1,0,0,1}};
    Cave cave = new Cave(field,4,4,limitBrith, limitDeath);
    Assertions.assertEquals(4, cave.getRows());
    Assertions.assertEquals(4, cave.getCols());
    Assertions.assertEquals(7, cave.getLimitDeath());
    Assertions.assertEquals(2, cave.getLimitBirth());
  }
}
