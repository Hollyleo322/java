import java.util.List;
import main.demo.agent.Agent;
import main.demo.constants.Constants;
import main.demo.model.Field;
import main.demo.model.Point;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestAgent {
  private Agent agent;

  @BeforeEach
  public void init() {
    int[][] vertical = {{0, 0, 0, 1}, {1, 0, 1, 1}, {0, 1, 0, 1}, {0, 0, 0, 1}};
    int[][] horizontal = {{1, 0, 1, 0}, {0, 0, 1, 0}, {1, 1, 0, 1}, {1, 1, 1, 1}};
    agent = new Agent(4,4,0.1, 0.99, 0.2, new Point(0,0,null), new Point(3,3,null), new Field(vertical,horizontal,4,4));
  }
  @Test
  public void testAgent() {
    agent.train();
    List<Point> path = agent.getPath();
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

  @Test
  public void testBestAction() {
    Assertions.assertEquals(Constants.RIGHT, agent.takeBestAction(0));
  }
  @Test
  public void testAvailableMove() {
    Assertions.assertTrue(agent.availableMove(0, Constants.RIGHT));
    Assertions.assertFalse(agent.availableMove(0, Constants.LEFT));
    Assertions.assertFalse(agent.availableMove(0, Constants.UP));
    Assertions.assertFalse(agent.availableMove(0, Constants.DOWN));

  }
  @Test
  public void testInside() {
    Assertions.assertTrue(agent.isInside(0, 0));
    Assertions.assertFalse(agent.isInside(40,50));
    Assertions.assertFalse(agent.isInside(-1,4));
    Assertions.assertFalse(agent.isInside(3,-50));
  }
  @Test
  public void testReward(){
    Assertions.assertEquals(-1, agent.getReward(0));
    Assertions.assertEquals(100, agent.getReward(15));
  }
  @Test
  public void testGetQValue() {
    Assertions.assertEquals(0, agent.getQValue(0, Constants.RIGHT));
  }
  @Test
  public void testNextState() {
    Assertions.assertEquals(1, agent.getNextState(0, Constants.RIGHT));
    Assertions.assertEquals(7, agent.getNextState(3, Constants.DOWN));
  }
  @Test
  public void updateQValue() {
    agent.updateQValue(0, Constants.RIGHT);
    Assertions.assertEquals(-0.1, agent.getQValue(0, Constants.RIGHT));
  }
  @Test
  public void testInEnd() {
    Assertions.assertTrue(agent.inEnd(15));
    Assertions.assertFalse(agent.inEnd(12));
  }
}
