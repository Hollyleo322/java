package com.example.bank_rest.security.jwt.filter;

import com.example.bank_rest.exception.TokenInvalidException;
import com.example.bank_rest.security.jwt.provider.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

  private final JwtProvider jwtProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String endpoint = request.getRequestURI();
    if (endpoint.equals("/api/bank_rest/user/registration") || endpoint.equals(
        "/api/bank_rest/user/auth") || endpoint.equals("/api/bank_rest/user/updateAccess")) {
      filterChain.doFilter(request, response);
    } else {
      String token = request.getHeader("Authorization");
      log.info("In filter token {}", token);
      if (token != null && token.startsWith("Bearer ")) {
        token = token.substring(7);
        try {
          var claims = jwtProvider.getClaims(token);
          UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
              claims.get("name"), null, jwtProvider.getRoles(token).stream().map(
              SimpleGrantedAuthority::new).toList());
          SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
          SecurityContextHolder.getContext().getAuthentication().getAuthorities().forEach((System.out::println));
          log.info("Role is {}", jwtProvider.getRoles(token));
          filterChain.doFilter(request, response);
        } catch (TokenInvalidException e) {
          log.info("Token is invalid");
          response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
          response.getWriter().println("Token is invalid");
        }
      }else {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().println("Header Authorization is incorrect");
      }
    }
  }
}
