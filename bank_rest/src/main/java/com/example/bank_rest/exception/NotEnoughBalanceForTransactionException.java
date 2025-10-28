package com.example.bank_rest.exception;

public class NotEnoughBalanceForTransactionException extends RuntimeException {

  public NotEnoughBalanceForTransactionException(String message) {
    super(message);
  }
}
