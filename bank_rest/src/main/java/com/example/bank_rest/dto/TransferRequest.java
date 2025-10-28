package com.example.bank_rest.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {
  private String fromNumber;
  private String toNumber;
  private BigDecimal sum;
}
