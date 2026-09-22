package com.example.bank_rest.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Card {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String number;
  @ManyToOne
  @JoinColumn(name = "owner_id")
  private User owner;
  @Column(nullable = false)
  private Integer expiryMonth;
  @Column(nullable = false)
  private Integer expiryYear;
  @Column(name ="card_status", nullable = false)
  @Enumerated(EnumType.STRING)
  private CardStatus status;
  @Column(nullable = false)
  private BigDecimal balance;

  public Card(User user, Integer expiryMonth, Integer expiryYear, CardStatus status, BigDecimal balance) {
    this.number = "";
    this.owner = user;
    this.expiryMonth = expiryMonth;
    this.expiryYear = expiryYear;
    this.status = status;
    this.balance = balance;
  }
}
