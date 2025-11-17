package com.hollyleo.api_gateway.controller;

import com.hollyleo.api_gateway.exception.IncorrectUsernameOrPasswordException;
import com.hollyleo.api_gateway.exception.UsernameAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
  @ExceptionHandler(UsernameAlreadyExistException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  String usernameExists(UsernameAlreadyExistException e){
    return e.getMessage();
  }
  @ExceptionHandler(IncorrectUsernameOrPasswordException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  String incorrectUsernameOrPassword(IncorrectUsernameOrPasswordException e) {
    return e.getMessage();
  }
}
