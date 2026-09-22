package wrapper.jna;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import lombok.AllArgsConstructor;
import wrapper.main.MainWrapper;
import wrapper.mapper.WrapperMapper;
import wrapper.model.WrapperGameState;

@AllArgsConstructor
public class UpdateStateRequest implements UpdateState {

  private MainWrapper mainWrapper;
  private HttpClient httpClient;

  @Override
  public JnaStructureOfC invoke() {
    HttpRequest request = HttpRequest.newBuilder(
        URI.create("http://localhost:8080/api/updatestate")).build();
    HttpRequest getStateRequest = HttpRequest.newBuilder(
        URI.create("http://localhost:8080/api/state")).build();
    try {
      httpClient.send(request, BodyHandlers.ofString());
      HttpResponse<String> response = httpClient.send(getStateRequest, BodyHandlers.ofString());
      WrapperGameState gameState = WrapperMapper.convert(response);
      mainWrapper.write(gameState);
      mainWrapper.getJnaStructure().write();
    } catch (Exception e) {
      System.out.println(e.getClass().toString() + " " + e.getMessage());
    }
    return mainWrapper.getJnaStructure();
  }
}
