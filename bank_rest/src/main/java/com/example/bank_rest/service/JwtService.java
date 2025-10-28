package com.example.bank_rest.service;

import com.example.bank_rest.dto.UserDto;
import com.example.bank_rest.entity.Role;
import com.example.bank_rest.exception.IncorrectUsernameOrPasswordException;
import com.example.bank_rest.exception.TokenInvalidException;
import com.example.bank_rest.security.jwt.jwtentity.JwtTokenService;
import com.example.bank_rest.security.jwt.jwtentity.JwtUser;
import com.example.bank_rest.security.jwt.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {

  private final AuthenticationManager authenticationManager;

  private final JwtProvider jwtProvider;

  private final UserService userService;

  public JwtTokenService createToken(UserDto user) throws IncorrectUsernameOrPasswordException {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
    } catch (AuthenticationException e) {
      throw new IncorrectUsernameOrPasswordException("Username or password is incorrect");
    }
    var jwtUser = new JwtUser(user.getUsername());
    userService.getRolesByUsername(jwtUser.getUsername()).forEach((role) -> jwtUser.getRoleList().add(role));
    return new JwtTokenService(jwtProvider.generateAccessToken(jwtUser),
        jwtProvider.generateRefreshToken(jwtUser));
  }

  public JwtTokenService updateAccess(String refreshToken) throws TokenInvalidException {
    try {
      var claims = jwtProvider.getClaims(refreshToken);
      JwtUser jwtUser = new JwtUser(claims.get("name", String.class));
      userService.getRolesByUsername(jwtUser.getUsername()).forEach((role) -> jwtUser.getRoleList().add(role));
      return new JwtTokenService(jwtProvider.generateAccessToken(jwtUser),
          jwtProvider.generateRefreshToken(jwtUser));
    } catch (TokenInvalidException e) {
      throw new TokenInvalidException(e.getMessage());
    }
  }
}
