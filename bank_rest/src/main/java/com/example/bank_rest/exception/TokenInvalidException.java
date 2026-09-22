package com.example.bank_rest.exception;

public class TokenInvalidException extends RuntimeException {

  public TokenInvalidException(String message) {
    super(message);
  }
}
