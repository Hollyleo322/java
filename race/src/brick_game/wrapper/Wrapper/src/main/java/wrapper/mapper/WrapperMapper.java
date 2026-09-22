package wrapper.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.http.HttpResponse;
import wrapper.model.WrapperGameState;

public class WrapperMapper {

  public static WrapperGameState convert(HttpResponse<String> response) {
    ObjectMapper objectMapper = new ObjectMapper();
    WrapperGameState gameState = new WrapperGameState();
    try {
      gameState = objectMapper.readValue(response.body(), WrapperGameState.class);
    } catch (Exception e) {
      System.out.println(e.getClass().toString() + " " + e.getMessage());
    }
    return gameState;
  }

  public static Integer convertInt(HttpResponse<String> response) {
    Integer result = 0;
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      result = objectMapper.readValue(response.body(), Integer.class);
    } catch (Exception e) {
      System.out.println(e.getClass().toString() + e.getMessage());
    }
    return result;
  }

}
