package di;

import datasource.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import web.filter.AuthFilter;


@Configuration
public class SpringConfiguration {
    private final UserRepository userRepository;

    public SpringConfiguration(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                .anyRequest().permitAll());
        http.addFilterBefore(new AuthFilter(userRepository), AuthorizationFilter.class);
        return http.build();
    }

}
