package controller;

import exception.GameIsAlreadyLaunched;
import exception.GameIsNotLaunched;
import exception.GameWithIdNotFound;
import exception.IncorrectDataInBody;
import model.WebErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestControllerException {

  @ExceptionHandler(GameWithIdNotFound.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String gameWithIdNotFound() {
    var error = new WebErrorMessage("Game with specified id not found");
    return error.getMessage();
  }

  @ExceptionHandler(GameIsAlreadyLaunched.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  String gameIsAlreadyLaunched() {
    var error = new WebErrorMessage("Game is already launched");
    return error.getMessage();
  }

  @ExceptionHandler(IncorrectDataInBody.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  String incorrectDataInBody() {
    var error = new WebErrorMessage("Incorrect date in body");
    return error.getMessage();
  }

  @ExceptionHandler(GameIsNotLaunched.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  String gameIsNotLaunched() {
    var error = new WebErrorMessage("Game is not launched");
    return error.getMessage();
  }
}
