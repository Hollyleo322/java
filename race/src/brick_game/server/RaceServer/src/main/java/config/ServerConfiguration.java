package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class ServerConfiguration implements WebMvcConfigurer {

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    String startpoint = "file:/";
    String endpoint = "/src/brick_game/web_gui/gui";
    String path = System.getProperty("user.dir") + endpoint;
    String os = System.getProperty("os.name").toLowerCase();
    if (os.contains("lin")) {
      path = startpoint.substring(0, startpoint.length() - 1) + System.getProperty("user.dir")
          + endpoint;
      if (path.contains("server")) {
        int indexServer = path.indexOf("server");
        int indexWeb = path.indexOf("web_gui");
        path = path.substring(0, indexServer) + path.substring(indexWeb);
      }
    }
    registry.addResourceHandler("/play/**")
        .addResourceLocations(
            path);
  }

}
