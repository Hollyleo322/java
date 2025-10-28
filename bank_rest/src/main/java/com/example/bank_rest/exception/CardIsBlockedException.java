package com.example.bank_rest.exception;

public class CardIsBlockedException extends RuntimeException {

  public CardIsBlockedException(String message) {
    super(message);
  }
}
