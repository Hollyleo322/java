package com.example.bank_rest.service;

import com.example.bank_rest.entity.Card;
import com.example.bank_rest.entity.CardStatus;
import com.example.bank_rest.event.BlockCardEvent;
import com.example.bank_rest.exception.CardIsBlockedException;
import com.example.bank_rest.exception.EncrypErrorException;
import com.example.bank_rest.exception.NotEnoughBalanceForTransactionException;
import com.example.bank_rest.exception.UserNotFoundException;
import com.example.bank_rest.exception.UserNotHaveCardWithNumberException;
import com.example.bank_rest.repository.CardRepository;
import com.example.bank_rest.security.jwt.provider.JwtProvider;
import com.example.bank_rest.util.Encryption;
import com.example.bank_rest.util.Masking;
import java.math.BigDecimal;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardService {

  private final CardRepository cardRepository;
  private final Encryption encryption;
  private final UserService userService;
  private final JwtProvider jwtProvider;
  private final ApplicationEventPublisher applicationEventPublisher;

  @Transactional
  public Card createCard(Long ownerID) throws UserNotFoundException, EncrypErrorException {
    var owner = userService.findById(ownerID);
    Calendar now = Calendar.getInstance();
    Card newCard = new Card(owner, now.get(Calendar.MONTH) + 1, now.get(Calendar.YEAR) + 5, CardStatus.BLOCKED, new BigDecimal(100));
    Card result;
    try {
      String number = createEncryptUniqueCardNumber();
      newCard.setNumber(number);
      newCard = cardRepository.save(newCard);
      result = new Card(newCard.getOwner(), newCard.getExpiryMonth(), newCard.getExpiryYear(), newCard.getStatus(),newCard.getBalance());
      result.setNumber(Masking.maskingNumber(encryption.decryptCardNumber(newCard.getNumber())));
    } catch (InvalidAlgorithmParameterException e) {
      throw new EncrypErrorException("Invalid alg");
    } catch (InvalidKeyException e) {
      throw new EncrypErrorException("Invalid key");
    } catch (IllegalBlockSizeException e) {
      throw new EncrypErrorException("Illegal block size");
    } catch (BadPaddingException e) {
      throw new EncrypErrorException("Bad Padding");
    }
    log.info("Created card for owner {}", owner);
    return result;
  }
  public String createCardNumber() {
    SecureRandom random = new SecureRandom();
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < 16; i++) {
      int number = random.nextInt(10);
      stringBuilder.append(number);
    }
    return stringBuilder.toString();
  }

  public String createEncryptUniqueCardNumber()
      throws InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
    String result = "";
    do {
      result = createCardNumber();
    } while (cardRepository.existsByNumber(encryption.encryptCardNumber(result)));
    return encryption.encryptCardNumber(result);
  }
  @Transactional
  public Card blockCard(Long ownerId, String number) throws UserNotFoundException, EncrypErrorException, NoSuchElementException {
    var cards = getCardByCardNumber(ownerId, number);
    var card = cards.get(0);
    card.setStatus(CardStatus.BLOCKED);
    Card result = new Card(card.getOwner(), card.getExpiryMonth(), card.getExpiryYear(), card.getStatus(), card.getBalance());
    try {
      result.setNumber(Masking.maskingNumber(encryption.decryptCardNumber(card.getNumber())));
    }catch (InvalidAlgorithmParameterException | BadPaddingException |
            IllegalBlockSizeException | InvalidKeyException e) {
      throw new EncrypErrorException("Error of decryption");
    }
    log.info("Blocked card with number {}", result.getNumber());
    return result;
  }
  @Transactional
  public Card activeCard(Long ownerId, String number) throws UserNotFoundException, EncrypErrorException, NoSuchElementException {
    var cards = getCardByCardNumber(ownerId, number);
    var card = cards.get(0);
    card.setStatus(CardStatus.ACTIVE);
    Card result = new Card(card.getOwner(), card.getExpiryMonth(), card.getExpiryYear(), card.getStatus(), card.getBalance());
    try {
      result.setNumber(Masking.maskingNumber(encryption.decryptCardNumber(card.getNumber())));
    }catch (InvalidAlgorithmParameterException | BadPaddingException |
            IllegalBlockSizeException | InvalidKeyException e) {
      throw new EncrypErrorException("Error of decryption");
    }
    log.info("Activated card with number {}", result.getNumber());
    return result;
  }

  public List<Card> getCardByCardNumber(Long ownerId, String number) throws  UserNotFoundException, EncrypErrorException{
    number = "************" + number;
    var owner = userService.findById(ownerId);
    String finalNumber = number;
    return owner.getCardList().stream().filter((itCard) -> {
      try {
        return Masking.maskingNumber(encryption.decryptCardNumber(itCard.getNumber())).equals(
            finalNumber);
      } catch (InvalidAlgorithmParameterException | BadPaddingException |
               IllegalBlockSizeException | InvalidKeyException e) {
        throw new EncrypErrorException("Error of decryption");
      }
    }).collect(Collectors.toCollection(ArrayList::new));
  }
  @Transactional
  public void deleteCard(Long ownerId, String number) throws  UserNotFoundException, EncrypErrorException, NoSuchElementException  {
    var cards = getCardByCardNumber(ownerId, number);
    cardRepository.deleteById(cards.get(0).getId());
    var user = userService.findById(ownerId);
    user.getCardList().remove(cards.get(0));
    log.info("Deleted card with number {}, owner {}", number, user);
  }


  public List<Card> getAllCards() {
    var cards = cardRepository.findAll();
    maskDecryptNumber(cards);
    return cards;
  }

  public List<Card> getAllUserCards(String header) throws IndexOutOfBoundsException, UserNotFoundException, EncrypErrorException {
    var user = userService.findByUsername(getUsernameFromHeader(header));
    var cards = cardRepository.findAllByOwnerId(user.getId());
    maskDecryptNumber(cards);
    return cards;
  }
  private void maskDecryptNumber(Iterable<Card> cards) {
    cards.forEach((card) -> {
      try {
        card.setNumber(Masking.maskingNumber(encryption.decryptCardNumber(card.getNumber())));
      } catch (InvalidAlgorithmParameterException | IllegalBlockSizeException |
               BadPaddingException | InvalidKeyException e) {
        throw new EncrypErrorException("Error of encryption");
      }
    });
  }
  public List<Card> getCardByNumber(String header, String number) throws IndexOutOfBoundsException, UserNotFoundException, EncrypErrorException, UserNotHaveCardWithNumberException {
    var user = userService.findByUsername(getUsernameFromHeader(header));
    var cards = getCardByCardNumber(user.getId(), number);
    if (cards.isEmpty()) {
      throw new UserNotHaveCardWithNumberException("User doesn't have card with number " + number);
    }
    maskDecryptNumber(cards);
    return cards;
  }
  public Page<Card> getPageableCards(String header, Integer page, Integer size) throws  IndexOutOfBoundsException,UserNotFoundException, EncrypErrorException {
    Pageable pageable = PageRequest.of(page, size);
    var user = userService.findByUsername(getUsernameFromHeader(header));
    maskDecryptNumber(user.getCardList());
    return new PageImpl<>(user.getCardList(), pageable, 5);
  }
  private String getUsernameFromHeader(String header) throws IndexOutOfBoundsException {
    return jwtProvider.getUsername(header.substring(7));
  }
  public void publishBlock(String header, String number) {
    var user = userService.findByUsername(getUsernameFromHeader(header));
    var cards = getCardByCardNumber(user.getId(), number);
    if (cards.isEmpty()) {
      throw new UserNotHaveCardWithNumberException("User doesn't have card with number " + number);
    }
    applicationEventPublisher.publishEvent(new BlockCardEvent(this, number, user.getId()));
  }

  @Transactional
  public List<Card> transfer(String header, String from, String to, BigDecimal sum) throws UserNotFoundException, EncrypErrorException, UserNotHaveCardWithNumberException, NotEnoughBalanceForTransactionException, CardIsBlockedException{
    var user = userService.findByUsername(getUsernameFromHeader(header));
    var cardsFrom = getCardByCardNumber(user.getId(), from);
    var cardsTo = getCardByCardNumber(user.getId(), to);
    if (cardsFrom.isEmpty()) {
      throw new UserNotHaveCardWithNumberException("User doesn't have card with number " + from);
    }
    if (cardsTo.isEmpty()) {
      throw new UserNotHaveCardWithNumberException("User doesn't have card with number " + to);
    }
    var cardFrom = cardsFrom.get(0);
    var cardTo = cardsTo.get(0);
    if (cardFrom.getBalance().compareTo(sum) >= 0 ) {
      if (cardFrom.getStatus().equals(CardStatus.ACTIVE) && cardTo.getStatus().equals(CardStatus.ACTIVE)) {
        cardFrom.setBalance(cardFrom.getBalance().subtract(sum));
        cardTo.setBalance(cardTo.getBalance().add(sum));
      } else {
        throw new CardIsBlockedException("Card is blocked");
      }
    }
    else {
      throw new NotEnoughBalanceForTransactionException("Not enough balance for transaction from card with number " + from);
    }
    var resultCardFrom = new Card(cardFrom.getOwner(), cardFrom.getExpiryMonth(), cardFrom.getExpiryYear(), cardFrom.getStatus(),
        cardFrom.getBalance());
    var resultCardTo = new Card(cardTo.getOwner(), cardTo.getExpiryMonth(), cardTo.getExpiryYear(), cardTo.getStatus(), cardTo.getBalance());
    try {
      resultCardFrom.setNumber(Masking.maskingNumber(encryption.decryptCardNumber(cardFrom.getNumber())));
      resultCardTo.setNumber(Masking.maskingNumber(encryption.decryptCardNumber(cardTo.getNumber())));
    }catch (InvalidAlgorithmParameterException | BadPaddingException |
            IllegalBlockSizeException | InvalidKeyException e) {
      throw new EncrypErrorException("Error of decryption");
    }
    return List.of(resultCardFrom, resultCardTo);
  }
  public Card checkBalance(String header, String number) throws IndexOutOfBoundsException, UserNotFoundException {
    var user = userService.findByUsername(getUsernameFromHeader(header));
    return getCardByCardNumber(user.getId(), number).get(0);
  }
}
