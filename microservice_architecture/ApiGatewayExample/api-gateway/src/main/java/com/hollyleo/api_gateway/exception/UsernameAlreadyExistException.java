package com.hollyleo.api_gateway.exception;

public class UsernameAlreadyExistException extends RuntimeException {
  public UsernameAlreadyExistException(String message) {
    super(message);
  }
}
