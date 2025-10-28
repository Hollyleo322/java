package com.example.bank_rest.controller;

import com.example.bank_rest.dto.CardDto;
import com.example.bank_rest.dto.CardRequest;
import com.example.bank_rest.dto.CreateCardRequest;
import com.example.bank_rest.exception.EncrypErrorException;
import com.example.bank_rest.exception.UserNotFoundException;
import com.example.bank_rest.service.CardService;
import com.example.bank_rest.util.Masking;
import jakarta.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bank_rest/card")
@RequiredArgsConstructor
public class CardController {
  private final CardService cardService;

  @PostMapping("/create")
  public ResponseEntity<?> createCard(@Valid @RequestBody CreateCardRequest createCardRequest) {
    try {
      var card = cardService.createCard(createCardRequest.getOwnerId());
      return ResponseEntity.status(HttpStatus.CREATED).body(new CardDto(
          Masking.maskingNumber(card.getNumber()), card.getOwner().getUsername(), card.getStatus(), card.getExpiryMonth(),
          card.getExpiryYear(), card.getBalance()));
    } catch (UserNotFoundException e) {
      return ResponseEntity.badRequest().body("User not found");
    } catch (EncrypErrorException e) {
      return ResponseEntity.internalServerError().body(e.getMessage());
    }
  }

  @PostMapping("/block")
  public ResponseEntity<?> blockCard(@Valid @RequestBody CardRequest cardRequest) {
    try {
      var card = cardService.blockCard(cardRequest.getOwnerId(), cardRequest.getNumber());
      return ResponseEntity.ok(new CardDto(card.getNumber(), card.getOwner().getUsername(), card.getStatus(), card.getExpiryMonth(), card.getExpiryYear(),card.getBalance()));
    } catch (UserNotFoundException e) {
      return ResponseEntity.badRequest().body("User not found");
    } catch (EncrypErrorException e) {
      return ResponseEntity.internalServerError().body(e.getMessage());
    } catch (NoSuchElementException e) {
      return ResponseEntity.badRequest().body("User doesn't have card with number ended by " + cardRequest.getNumber());
    }
  }

  @PostMapping("/active")
  public ResponseEntity<?> activeCard(@Valid @RequestBody CardRequest cardRequest) {
    try {
      var card = cardService.activeCard(cardRequest.getOwnerId(), cardRequest.getNumber());
      return ResponseEntity.ok(new CardDto(card.getNumber(), card.getOwner().getUsername(), card.getStatus(), card.getExpiryMonth(), card.getExpiryYear(), card.getBalance()));
    } catch (UserNotFoundException e) {
      return ResponseEntity.badRequest().body("User not found");
    } catch (EncrypErrorException e) {
      return ResponseEntity.internalServerError().body(e.getMessage());
    } catch (NoSuchElementException e) {
      return ResponseEntity.badRequest().body("User doesn't have card with number ended by " + cardRequest.getNumber());
    }
  }
  @PostMapping("/delete")
  public ResponseEntity<?> deleteCard(@Valid @RequestBody CardRequest cardRequest) {
    try {
      cardService.deleteCard(cardRequest.getOwnerId(), cardRequest.getNumber());
      return ResponseEntity.ok("Card with number " + cardRequest.getNumber() + " deleted");
    }catch (UserNotFoundException e) {
      return ResponseEntity.badRequest().body("User not found");
    } catch (EncrypErrorException e) {
      return ResponseEntity.internalServerError().body(e.getMessage());
    } catch (NoSuchElementException e) {
      return ResponseEntity.badRequest().body("User doesn't have card with number ended by " + cardRequest.getNumber());
    }
  }
  @GetMapping("/all")
  public ResponseEntity<List<CardDto>> getAllCards() {
    return ResponseEntity.ok(cardService.getAllCards().stream().map((card) -> new CardDto(card.getNumber(), card.getOwner().getUsername(), card.getStatus(), card.getExpiryMonth(), card.getExpiryYear(), card.getBalance())).toList());
  }

}
