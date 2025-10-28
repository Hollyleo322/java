package com.example.bank_rest.controller;

import com.example.bank_rest.exception.UserNotHaveCardWithNumberException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  String notValidArgument(MethodArgumentNotValidException e) {
    StringBuilder stringBuilder = new StringBuilder();
    e.getBindingResult().getFieldErrors().forEach((error) -> stringBuilder.append(
        error.getDefaultMessage()).append(" "));
    return stringBuilder.toString();
  }
  @ExceptionHandler(UserNotHaveCardWithNumberException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  String notValidQueryArgument(UserNotHaveCardWithNumberException e) {
    return e.getMessage();
  }
}
