import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"datasource.repository", "di", "web", "domain"})
@EnableJpaRepositories(basePackages = "datasource.repository")
@EntityScan(basePackages = "datasource.model")
public class ApplicationSpring {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationSpring.class, args);
    }
}
