package com.hollyleo.api_gateway.controller;

import com.hollyleo.api_gateway.dto.AuthRequest;
import com.hollyleo.api_gateway.dto.JwtResponse;
import com.hollyleo.api_gateway.exception.IncorrectUsernameOrPasswordException;
import com.hollyleo.api_gateway.repository.UserRepository;
import com.hollyleo.api_gateway.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  private final UserRepository userRepository;

  @PostMapping("/authentication")
  ResponseEntity<JwtResponse> authentication(@RequestBody AuthRequest authRequest) {
    try {
      var response = authService.authentication(authRequest);
      return ResponseEntity.ok(response);
    }catch (IncorrectUsernameOrPasswordException e) {
      throw new IncorrectUsernameOrPasswordException(e.getMessage());
    }
  }
  @PostMapping("/registration")
  ResponseEntity<String> registration(@RequestBody AuthRequest authRequest) {

  }
}
