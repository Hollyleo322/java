package di;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import web.filter.AuthFilter;
import web.provider.JwtProvider;


@Configuration
public class SpringConfiguration {

    private final JwtProvider jwtProvider;

    public SpringConfiguration(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                .requestMatchers("/authorization", "/registration", "/updateAccessToken").permitAll()
                .anyRequest().authenticated());
        http.addFilterBefore(new AuthFilter(jwtProvider), AuthorizationFilter.class);
        return http.build();
    }

}
