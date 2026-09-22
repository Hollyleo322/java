package wrapper.main;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import lombok.Getter;
import lombok.Setter;
import wrapper.constant.Constants;
import wrapper.jna.GetStateRequest;
import wrapper.jna.JNALibLoader;
import wrapper.jna.JnaCLibrary;
import wrapper.jna.JnaStructureOfC;
import wrapper.jna.UpdateStateRequest;
import wrapper.jna.UserInputRequest;
import wrapper.mapper.WrapperMapper;
import wrapper.model.WrapperGameState;
import wrapper.request.StartRaceRequest;

@Setter
@Getter
public class MainWrapper {

  private UpdateStateRequest updateStateRequest;
  private JnaStructureOfC jnaStructure;
  private UserInputRequest userInputRequest;
  private GetStateRequest getStateRequest;
  private HttpClient httpClient = HttpClient.newBuilder().build();
  private StartRaceRequest startRaceRequest;

  public MainWrapper() {
    updateStateRequest = new UpdateStateRequest(this, httpClient);
    JnaCLibrary lib = JNALibLoader.getInstance();
    jnaStructure = lib.getStatePtr();
    userInputRequest = new UserInputRequest(this, httpClient);
    getStateRequest = new GetStateRequest(this, httpClient);
    lib.initUserAction(userInputRequest, updateStateRequest, getStateRequest);
    startRaceRequest = new StartRaceRequest(httpClient);
  }

  public void startRace() {
    startRaceRequest.send();
  }

  public void write(WrapperGameState gameState) {
    for (int i = 0; i < Constants.HEIGHT; i++) {
      Pointer pt = jnaStructure.field.getPointer(i * Native.POINTER_SIZE);
      int[] array = getIntData(gameState.getField(), i, Constants.WIDTH);
      pt.write(0, array, 0, Constants.WIDTH);
    }
    for (int i = 0; i < Constants.WIDTHFORNEXT; i++) {
      Pointer pt = jnaStructure.next.getPointer(i * Native.POINTER_SIZE);
      int[] array = getIntData(gameState.getNext(), i, Constants.WIDTHFORNEXT);
      pt.write(0, array, 0, Constants.WIDTHFORNEXT);
    }
    jnaStructure.high_score = gameState.getHigh_score();
    jnaStructure.level = gameState.getLvl();
    jnaStructure.score = gameState.getScore();
    jnaStructure.speed = gameState.getSpeed();
    if (gameState.isPause()) {
      jnaStructure.pause = 1;
    } else {
      jnaStructure.pause = 0;
      if (getCondition() != 0) {
        jnaStructure.pause = getCondition();
      }
    }
  }

  public int getCondition() {
    int result = 0;
    HttpRequest request = HttpRequest.newBuilder(
        URI.create("http://localhost:8080/api/condition-of-race")).build();
    try {
      HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
      result = WrapperMapper.convertInt(response);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return result;
  }

  private int[] getIntData(boolean[][] field, int i, int width) {
    int[] result = new int[width];
    for (int j = 0; j < width; j++) {
      if (field[i][j]) {
        result[j] = 1;
      } else {
        result[j] = 0;
      }
    }
    return result;
  }

}
