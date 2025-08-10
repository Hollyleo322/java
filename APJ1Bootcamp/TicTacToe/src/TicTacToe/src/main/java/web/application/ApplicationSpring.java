package web.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"di", "web"})
public class ApplicationSpring {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationSpring.class, args);
    }
}
