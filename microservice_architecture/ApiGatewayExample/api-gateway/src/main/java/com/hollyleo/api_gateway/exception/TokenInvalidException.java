package com.hollyleo.api_gateway.exception;

public class TokenInvalidException extends RuntimeException {

  public TokenInvalidException(String message) {
    super(message);
  }
}
