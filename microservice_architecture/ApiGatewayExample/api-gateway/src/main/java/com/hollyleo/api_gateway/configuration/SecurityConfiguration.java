package com.hollyleo.api_gateway.configuration;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

import com.hollyleo.api_gateway.security.details.service.MyUserDetailsService;
import com.hollyleo.api_gateway.security.jwt.JwtProvider;
import com.hollyleo.api_gateway.security.jwt.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

  private final MyUserDetailsService myUserDetailsService;

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12);
  }

  @Bean
  AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(
        myUserDetailsService);
    authenticationProvider.setPasswordEncoder(passwordEncoder);
    return new ProviderManager(authenticationProvider);
  }
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, JwtProvider jwtProvider) throws Exception {
    httpSecurity.csrf(AbstractHttpConfigurer::disable);
    httpSecurity.authorizeHttpRequests((requestRegistry) -> requestRegistry
        .requestMatchers("/registration", "/auth").permitAll()
        .anyRequest().authenticated());
    httpSecurity.addFilterBefore(new JwtFilter(jwtProvider), AuthorizationFilter.class);
    return httpSecurity.build();
  }

}
