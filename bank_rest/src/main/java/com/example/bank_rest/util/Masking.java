package com.example.bank_rest.util;

public class Masking {

  public static String maskingNumber(String number) {
    return "************" + number.substring(12);
  }

}
