package com.hollyleo.api_gateway.security.jwt;

import com.hollyleo.api_gateway.dto.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

  private final JwtBuilder jwtBuilder = Jwts.builder();

  @Value("${app.security.key}")
  private String secretString;

  private  SecretKey secretKey;

  @PostConstruct
  public void init() {
    secretKey = Keys.hmacShaKeyFor(secretString.getBytes());
  }

  public String generateAccessToken(JwtUser user) {
    return jwtBuilder.claim("type", "access").claim("username", user.getUsername()).claim("roles", user.getRoles()).issuer("api-gateway-hollyleo").issuedAt(new Date()).expiration(new Date(System.currentTimeMillis() + 15 * 60 * 1000)).signWith(secretKey).compact();
  }

  public String generateRefreshToken(JwtUser user) {
    return jwtBuilder.claim("type", "refresh").claim("username", user.getUsername()).claim("roles", user.getRoles()).issuer("api-gateway-hollyleo").issuedAt(new Date()).expiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000)).signWith(secretKey).compact();
  }

  public Claims getClaims(String token) {
    return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
  }
  public List<String> getRoles(String token) {
    var claims = getClaims(token);
    return claims.get("roles", List.class);
  }

  String getUsername(String token) {
    var claims = getClaims(token);
    return claims.get("username", String.class);
  }
}
