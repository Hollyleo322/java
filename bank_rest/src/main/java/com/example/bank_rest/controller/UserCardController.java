package com.example.bank_rest.controller;

import com.example.bank_rest.dto.CardDto;
import com.example.bank_rest.dto.TransferRequest;
import com.example.bank_rest.exception.CardIsBlockedException;
import com.example.bank_rest.exception.EncrypErrorException;
import com.example.bank_rest.exception.NotEnoughBalanceForTransactionException;
import com.example.bank_rest.exception.UserNotFoundException;
import com.example.bank_rest.exception.UserNotHaveCardWithNumberException;
import com.example.bank_rest.service.CardService;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bank_rest/card/user")
@RequiredArgsConstructor
public class UserCardController {
  private final CardService cardService;

  @GetMapping("/all")
  public ResponseEntity<?> getAllUserCards(@RequestHeader("Authorization") String headerAuthorization) {
    try {
      return ResponseEntity.ok(cardService.getAllUserCards(headerAuthorization).stream().map((card) -> new CardDto(card.getNumber(), card.getOwner().getUsername(), card.getStatus(), card.getExpiryMonth(), card.getExpiryYear(), card.getBalance())));
    } catch (IndexOutOfBoundsException e) {
      return ResponseEntity.badRequest().body("Token is invalid, or empty Authorization header");
    } catch (UserNotFoundException e) {
      return ResponseEntity.badRequest().body("User not found");
    } catch (EncrypErrorException e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  @GetMapping("/number/{cardNumber}")
  public ResponseEntity<?> getCardByNumber(@RequestHeader("Authorization") String headerAuthorization, @PathVariable String cardNumber) {
    try {
      return ResponseEntity.ok(cardService.getCardByNumber(headerAuthorization, cardNumber).stream().map((card) -> new CardDto(card.getNumber(), card.getOwner().getUsername(), card.getStatus(), card.getExpiryMonth(), card.getExpiryYear(),card.getBalance())));
    }catch (IndexOutOfBoundsException e) {
      return ResponseEntity.badRequest().body("Token is invalid, or empty Authorization header");
    } catch (UserNotFoundException e) {
      return ResponseEntity.badRequest().body("User not found");
    } catch (EncrypErrorException e) {
      return ResponseEntity.internalServerError().build();
    }
  }
  @GetMapping("/pageable")
  public ResponseEntity<?> getPageableCards(@RequestHeader("Authorization") String headerAuthorization, @RequestParam String page, @RequestParam String size) {
    try {
      return ResponseEntity.ok(cardService.getPageableCards(headerAuthorization, Integer.parseInt(page), Integer.parseInt(size)).map((card -> new CardDto(card.getNumber(), card.getOwner().getUsername(), card.getStatus(), card.getExpiryMonth(), card.getExpiryYear(), card.getBalance()))));
    }catch (IndexOutOfBoundsException e) {
      return ResponseEntity.badRequest().body("Token is invalid, or empty Authorization header");
    } catch (UserNotFoundException e) {
      return ResponseEntity.badRequest().body("User not found");
    } catch (EncrypErrorException e) {
      return ResponseEntity.internalServerError().build();
    }
  }
  @PostMapping("/block/{cardNumber}")
  public ResponseEntity<?> queryBlockCard(@RequestHeader("Authorization") String headerAuthorization, @PathVariable String cardNumber) {
    try {
      cardService.publishBlock(headerAuthorization, cardNumber);
      return ResponseEntity.ok("Query is sent");
    } catch (UserNotHaveCardWithNumberException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping("/transfer")
  public ResponseEntity<?> transferMoney(@RequestHeader("Authorization") String headerAuthorization, @RequestBody TransferRequest transferDto) {
    try {
      var cards = cardService.transfer(headerAuthorization, transferDto.getFromNumber(), transferDto.getToNumber(), transferDto.getSum());
      return ResponseEntity.ok(cards.stream().map(
          (card) -> new CardDto(card.getNumber(), card.getOwner().getUsername(), card.getStatus(),
              card.getExpiryMonth(), card.getExpiryYear(), card.getBalance())));
    } catch (UserNotFoundException e) {
      return ResponseEntity.badRequest().body("User not found");
    } catch (EncrypErrorException e) {
      return ResponseEntity.internalServerError().build();
    } catch (UserNotHaveCardWithNumberException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (CardIsBlockedException e) {
      return ResponseEntity.badRequest().body("Card is blocked");
    } catch (NotEnoughBalanceForTransactionException e) {
      return ResponseEntity.badRequest().body("Not enough money");
    }
  }

  @GetMapping("/balance/{cardNumber}")
  public ResponseEntity<?> checkBalance(@RequestHeader("Authorization") String headerAuthorization, @PathVariable String cardNumber) {
    try {
      var card = cardService.checkBalance(headerAuthorization, cardNumber);
      return ResponseEntity.ok(card.getBalance());
    } catch (UserNotHaveCardWithNumberException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
