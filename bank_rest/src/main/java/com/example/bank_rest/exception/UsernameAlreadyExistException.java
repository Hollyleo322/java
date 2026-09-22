package com.example.bank_rest.exception;

public class UsernameAlreadyExistException extends RuntimeException {

  public UsernameAlreadyExistException(String message) {
    super(message);
  }
}
