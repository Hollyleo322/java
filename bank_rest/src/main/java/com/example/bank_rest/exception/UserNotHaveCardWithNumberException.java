package com.example.bank_rest.exception;

public class UserNotHaveCardWithNumberException extends RuntimeException {

  public UserNotHaveCardWithNumberException(String message) {
    super(message);
  }
}
