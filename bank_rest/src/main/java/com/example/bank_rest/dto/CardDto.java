package com.example.bank_rest.dto;

import com.example.bank_rest.entity.CardStatus;
import java.math.BigDecimal;
import lombok.Data;


@Data
public class CardDto {
  private final  String number;
  private final String user;
  private final CardStatus cardStatus;
  private final Integer expiryMonth;
  private final Integer expiryYear;
  private final BigDecimal balance;
}
