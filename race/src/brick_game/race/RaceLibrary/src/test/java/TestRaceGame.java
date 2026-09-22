import com.sun.jna.Native;
import com.sun.jna.Pointer;
import constant.Constants;
import game.RaceGame;
import java.io.File;
import java.util.Arrays;
import jna.JNALib;
import model.Border;
import model.Car;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
public class TestRaceGame {

  @Test
  public void constructor() {
    RaceGame raceGame = new RaceGame();
    Assertions.assertNotNull(raceGame.getPlayer());
    Assertions.assertNotNull(raceGame.getRival());
    Assertions.assertNotNull(raceGame.getRaceService());
    Assertions.assertNotNull(raceGame.getUserAction());
    Assertions.assertNotNull(raceGame.getUpdateState());
    Assertions.assertEquals(1, raceGame.getRival().getRivals().size());
    Assertions.assertEquals(0, raceGame.getCondition());
  }
  @Test
  public void start() {
    RaceGame raceGame = new RaceGame();
    raceGame.start();
    Assertions.assertEquals(0, raceGame.getRaceService().getJnaState().high_score);
    Assertions.assertEquals(0, raceGame.getCondition());
    Assertions.assertEquals(0, raceGame.getRaceService().getJnaState().score);
    Assertions.assertEquals(1, raceGame.getRaceService().getJnaState().level);
    Assertions.assertFalse(raceGame.getRaceService().getJnaState().pause);
    Assertions.assertEquals(1, raceGame.getRaceService().getJnaState().speed);
    checkCar(raceGame.getPlayer().getCar(), raceGame.getRaceService().getJnaState().field);
    raceGame.getRival().getRivals().forEach((rival) ->{checkCar(rival, raceGame.getRaceService().getJnaState().field);});
    raceGame.getRaceService().getBorders().forEach((border -> {checkBorder(border,raceGame.getRaceService().getJnaState().field);}));
  }
  private void checkCar(Car car, Pointer field) {
    checkOnePoint(car, field, 0);
    checkThreePoints(car,field);
    checkOnePoint(car, field, 2);
    checkTwoPoints(car,field);
  }
  private void checkOnePoint(Car car, Pointer field, int adder){
    int [] actual = new int[Constants.WIDTH];
    Pointer pt = field.getPointer((car.getY() + adder) * Native.POINTER_SIZE);
    pt.read(0,actual,0,Constants.WIDTH);
    if (car.getX() == 0) {
      Assertions.assertEquals(1, actual[3]);
    } else {
      Assertions.assertEquals(1, actual[6]);
    }
  }
  private void checkThreePoints(Car car, Pointer field) {
    int [] actual = new int[Constants.WIDTH];
    Pointer pt = field.getPointer((car.getY() + 1) * Native.POINTER_SIZE);
    pt.read(0,actual,0,Constants.WIDTH);
    if (car.getX() == 0) {
      Assertions.assertEquals(1, actual[2]);
      Assertions.assertEquals(1, actual[3]);
      Assertions.assertEquals(1, actual[4]);
    }
    else {
      Assertions.assertEquals(1, actual[5]);
      Assertions.assertEquals(1, actual[6]);
      Assertions.assertEquals(1, actual[7]);
    }
  }
  private void checkTwoPoints(Car car, Pointer field) {
    int [] actual = new int[Constants.WIDTH];
    Pointer pt = field.getPointer((car.getY() + 3) * Native.POINTER_SIZE);
    pt.read(0,actual,0,Constants.WIDTH);
    if (car.getX() == 0) {
      Assertions.assertEquals(1, actual[2]);
      Assertions.assertEquals(1, actual[4]);
    }
    else {
      Assertions.assertEquals(1, actual[5]);
      Assertions.assertEquals(1, actual[7]);
    }
  }
  private void checkBorder(Border border, Pointer field) {
    int [] actual = new int[Constants.WIDTH];
    Pointer pt = field.getPointer((border.getY()) * Native.POINTER_SIZE);
    pt.read(0,actual,0,Constants.WIDTH);
    Assertions.assertEquals(1, actual[0]);
    Assertions.assertEquals(1, actual[1]);
    Assertions.assertEquals(1, actual[8]);
    Assertions.assertEquals(1, actual[9]);
  }
  @Test
  public void updateState() {
    RaceGame raceGame = new RaceGame();
    raceGame.start();
    raceGame.getRaceService().getJnaState().score = 4;
    raceGame.getRival().getRivals().add(new Car(1, 19));
    raceGame.updateState(false);
    raceGame.getRival().getRivals().forEach((rival) ->{checkCar(rival, raceGame.getRaceService().getJnaState().field);});
    raceGame.getRaceService().getBorders().forEach((border -> {checkBorder(border,raceGame.getRaceService().getJnaState().field);}));
    Assertions.assertEquals(5, raceGame.getRaceService().getJnaState().score);
    raceGame.getRival().getRivals().add(new Car(0, 18));
    JNALib.INSTANCE.updateCurrentState();
    Assertions.assertEquals(Constants.PRE_EXIT_SITUATION, raceGame.getCondition());
  }
  @Test
  public void action() {
    RaceGame raceGame = new RaceGame();
    raceGame.start();
    JNALib.INSTANCE.userInput(Constants.RIGHT, false);
    Assertions.assertEquals(1, raceGame.getPlayer().getCar().getX());
    raceGame.doAction(Constants.RIGHT);
    Assertions.assertEquals(1, raceGame.getPlayer().getCar().getX());
    raceGame.doAction(Constants.LEFT);
    Assertions.assertEquals(0, raceGame.getPlayer().getCar().getX());
    raceGame.doAction(Constants.LEFT);
    Assertions.assertEquals(0, raceGame.getPlayer().getCar().getX());
    raceGame.doAction(Constants.UP);
    Assertions.assertEquals(10, raceGame.getRaceService().getJnaState().speed);
    Assertions.assertEquals(1, raceGame.getRival().getRivals().get(0).getY());
    raceGame.doAction(Constants.PAUSE);
    Assertions.assertEquals(Constants.PAUSE, raceGame.getCondition());
    raceGame.doAction(Constants.PAUSE);
    Assertions.assertEquals(Constants.GAME, raceGame.getCondition());
    raceGame.doAction(Constants.TERMINATE);
    Assertions.assertEquals(Constants.END_OF_GAME, raceGame.getCondition());
    File file = new File("high_score.txt");
    Assertions.assertTrue(file.exists());
  }
  @Test
  public void testState() {
    RaceGame raceGame = new RaceGame();
    raceGame.start();
    var state = raceGame.getState();
    Assertions.assertNotNull(state.field());
    Assertions.assertNotNull(state.next());
    Assertions.assertEquals(0, state.score());
    Assertions.assertEquals(0, state.highScore());
    Assertions.assertEquals(1, state.level());
    Assertions.assertEquals(1, state.speed());
    Assertions.assertFalse(state.pause());
  }
}
