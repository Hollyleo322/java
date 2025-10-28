package com.example.bank_rest.controller;

import com.example.bank_rest.dto.CreateUserRequest;
import com.example.bank_rest.dto.UserDto;
import com.example.bank_rest.entity.User;
import com.example.bank_rest.exception.IncorrectUsernameOrPasswordException;
import com.example.bank_rest.exception.TokenInvalidException;
import com.example.bank_rest.exception.UsernameAlreadyExistException;
import com.example.bank_rest.dto.JwtRequest;
import com.example.bank_rest.dto.JwtResponse;
import com.example.bank_rest.dto.UpdateAccessRequest;
import com.example.bank_rest.service.JwtService;
import com.example.bank_rest.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bank_rest/user")
@RequiredArgsConstructor
public class AuthController {

  private final UserService userService;

  private final JwtService jwtService;

  @PostMapping("/registration")
  public ResponseEntity<String> createUser(@Valid @RequestBody CreateUserRequest userRequest) {
    var user = new User(userRequest.getUsername(), userRequest.getPassword());
    try {
      userService.create(user);
      return ResponseEntity.ok("User with name " + user.getUsername() + " was registered");
    } catch (UsernameAlreadyExistException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body("Username is already being used, please choose another username");
    }
  }

  @PostMapping("/auth")
  public ResponseEntity<?> authToken(@Valid @RequestBody JwtRequest jwtRequest) {
    try {
      var tokens = jwtService.createToken(
          new UserDto(jwtRequest.getUsername(), jwtRequest.getPassword()));
      return ResponseEntity.ok(new JwtResponse(tokens.getAccessToken(), tokens.getRefreshToken()));
    } catch (IncorrectUsernameOrPasswordException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password");
    }
  }

  @PostMapping("/updateAccess")
  public ResponseEntity<?> updateAccessToken(@Valid @RequestBody UpdateAccessRequest accessRequest) {
    try {
      var tokens = jwtService.updateAccess(accessRequest.getRefreshToken());
      return ResponseEntity.ok(new JwtResponse(tokens.getAccessToken(), tokens.getRefreshToken()));
    } catch (TokenInvalidException e) {
      return ResponseEntity.badRequest().body("Token is invalid");
    }
  }
}
