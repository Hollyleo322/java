package com.hollyleo.api_gateway.exception;

public class IncorrectUsernameOrPasswordException extends RuntimeException {
  public IncorrectUsernameOrPasswordException(String message) {
    super(message);
  }
}
