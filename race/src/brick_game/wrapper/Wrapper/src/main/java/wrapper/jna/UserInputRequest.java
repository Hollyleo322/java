package wrapper.jna;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import lombok.AllArgsConstructor;
import wrapper.main.MainWrapper;

@AllArgsConstructor
public class UserInputRequest implements UserInput {

  private final MainWrapper mainWrapper;
  private HttpClient httpClient;

  @Override
  public void action(int action, boolean hold) {
    HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/api/actions"))
        .header("Content-Type", "application/json").POST(
            BodyPublishers.ofString("{\"action_id\" : " + action + ",\n\"hold\" : " + hold + "}"))
        .build();
    try {
      httpClient.send(request, BodyHandlers.ofString());
    } catch (Exception e) {
      System.out.println(e.getClass().toString() + " " + e.getMessage());
    }
  }
}
