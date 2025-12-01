import main.demo.alg.Decision;
import main.demo.model.Field;
import main.demo.model.Point;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;

public class TestSolving {

  @Test
  public void testFindDecision() {
    int[][] vertical = {{0,0,0,1}, {1,0,1,1}, {0,1,0,1}, {0,0,0,1}};
    int[][] horizontal = {{1,0,1,0}, {0,0,1,0}, {1,1,0,1}, {1,1,1,1}};
    Decision decision = new Decision();
    decision.findDecision(new Field(vertical, horizontal,4,4), new Point(0,0, null), new Point(3,3,null));
    List<Point> path = decision.getPath();
    Assertions.assertEquals(3, path.get(0).x);
    Assertions.assertEquals(3, path.get(0).y);
    Assertions.assertEquals(2, path.get(1).x);
    Assertions.assertEquals(3, path.get(1).y);
    Assertions.assertEquals(2, path.get(2).x);
    Assertions.assertEquals(2, path.get(2).y);
    Assertions.assertEquals(3, path.get(3).x);
    Assertions.assertEquals(2, path.get(3).y);
    Assertions.assertEquals(3, path.get(4).x);
    Assertions.assertEquals(1, path.get(4).y);
    Assertions.assertEquals(3, path.get(5).x);
    Assertions.assertEquals(0, path.get(5).y);
    Assertions.assertEquals(2, path.get(6).x);
    Assertions.assertEquals(0, path.get(6).y);
    Assertions.assertEquals(1, path.get(7).x);
    Assertions.assertEquals(0, path.get(7).y);
  }
}
