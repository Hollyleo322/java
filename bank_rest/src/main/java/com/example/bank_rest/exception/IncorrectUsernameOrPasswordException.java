package com.example.bank_rest.exception;

public class IncorrectUsernameOrPasswordException extends RuntimeException {

  public IncorrectUsernameOrPasswordException(String message) {
    super(message);
  }
}
