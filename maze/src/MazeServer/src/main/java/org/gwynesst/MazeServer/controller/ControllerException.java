package org.gwynesst.MazeServer.controller;


import org.gwynesst.MazeServer.exception.NotLoadedField;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerException {

  @ExceptionHandler(NotLoadedField.class)
  @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
  String showNotLoaded(NotLoadedField e) {
    return e.getMessage();
  }
}
