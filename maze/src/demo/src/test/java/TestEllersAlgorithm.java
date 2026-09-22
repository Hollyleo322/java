import java.util.ArrayList;
import java.util.List;
import main.demo.alg.EllerAlgorithm;
import main.demo.model.Field;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestEllersAlgorithm {

  @Test
  public void testGetField() {
    Field result = EllerAlgorithm.getField(4, 4);
    Assertions.assertEquals(4, result.getRows());
    Assertions.assertEquals(4, result.getCols());
  }

  @Test
  public void testInitLastLine() {
    int[][] vertical = new int[2][2];
    int[] arraySet = new int[2];
    EllerAlgorithm.initLastLine(arraySet, vertical, 2, 1);
    int[] expected = {1, 0};
    for (int i = 0; i < 2; i++) {
      Assertions.assertEquals(expected[i], vertical[1][i]);
    }
    arraySet[0]= 0;
    arraySet[1] = 1;
    EllerAlgorithm.initLastLine(arraySet,vertical,2,1);
    int[] expectedSet = {0,0};
    for (int i = 0; i <2; i++) {
      Assertions.assertEquals(expectedSet[i], arraySet[i]);
    }
  }

  @Test
  public void testFillRight() {
    int[][] array = new int[4][4];
    EllerAlgorithm.fillRight(array, 4, 4);
    for (int i = 0; i < 4; i++) {
      Assertions.assertEquals(1, array[i][3]);
    }
  }

  @Test
  public void testFillBottom() {
    int[][] array = new int[4][4];
    EllerAlgorithm.fillBottom(array, 4, 4);
    for (int i = 0; i < 4; i++) {
      Assertions.assertEquals(1, array[3][i]);
    }
  }

  @Test
  public void testInitFirstLine() {
    int[] array = new int[5];
    EllerAlgorithm.initFirstLine(array, 5);
    for (int i = 0; i < 5; i++) {
      Assertions.assertEquals(i, array[i]);
    }
  }

  @Test
  public void testInitBottomLine() {
    int[][] horizontal = new int[4][4];
    int[] arraySets = new int[4];
    EllerAlgorithm.initFirstLine(arraySets, 4);
    for (int i = 0; i < 4; i++) {
      if (i % 2 == 1) {
        horizontal[1][i] = 1;
      }
    }
    EllerAlgorithm.initBottomLine(arraySets,horizontal, 4, 2 );
    for (int i = 0; i < 4; i++) {
      Assertions.assertEquals(i,arraySets[i]);
    }
  }
  @Test
  public void testIncrement() {
    List<Integer> list = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      list.add(i);
    }
    int result = EllerAlgorithm.incrementNumber(list, 0);
    Assertions.assertEquals(5, result);
  }
}
