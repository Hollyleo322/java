package wrapper.request;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StartRaceRequest {

  private HttpClient httpClient;

  public void send() {
    HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/api/games/3"))
        .POST(
            BodyPublishers.noBody())
        .build();
    try {
      HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
      if (response.statusCode() == 409) {
        try {
          HttpRequest resetRaceRequest = HttpRequest.newBuilder(
              URI.create("http://localhost:8080/api/reset-race")).build();
          httpClient.send(resetRaceRequest, BodyHandlers.ofString());
        } catch (Exception ex) {
          System.out.println(ex.getClass() + " " + ex.getMessage());
        }
      }
    } catch (Exception e) {
      System.out.println(e.getClass() + " " + e.getMessage());
    }
  }
}
