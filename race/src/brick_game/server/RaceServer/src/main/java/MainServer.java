import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"bean", "config", "controller", "service"})
public class MainServer {

  public static void main(String[] args) {
    SpringApplication.run(MainServer.class, args);
  }
}
