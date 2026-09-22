package com.example.bank_rest.security.jwt.provider;

import com.example.bank_rest.exception.TokenInvalidException;
import com.example.bank_rest.security.jwt.jwtentity.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.List;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

  private final JwtBuilder jwtBuilder = Jwts.builder();

  private String Key = "HelloIsMyKey322HelloIsMyKey322HelloIsMyKey322HelloIsMyKey322HelloIsMyKey322";

  private SecretKey secretKey = Keys.hmacShaKeyFor(Key.getBytes());


  public String generateAccessToken(JwtUser user) {
    return jwtBuilder.claim("name", user.getUsername()).claim("role", user.getRoleList()).issuer("bank_rest").issuedAt(new Date()).expiration(new Date(System.currentTimeMillis() + 15 * 60 * 1000)).signWith(secretKey).compact();
  }

  public String generateRefreshToken(JwtUser user) {
    return jwtBuilder.claim("name", user.getUsername()).issuer("bank_rest").issuedAt(new Date()).expiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000)).signWith(secretKey).compact();
  }

  public Claims getClaims(String token) throws TokenInvalidException {
    try {
      return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
    } catch (JwtException | IllegalArgumentException e) {
      throw new TokenInvalidException(e.getMessage());
    }
  }
  public List<String> getRoles(String token) {
    var claims = getClaims(token);
    return claims.get("role", List.class);
  }
  public String getUsername(String token) {
    var claims = getClaims(token);
    return claims.get("name", String.class);
  }
}
