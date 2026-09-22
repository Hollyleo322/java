package com.example.bank_rest;

import com.example.bank_rest.entity.Card;
import com.example.bank_rest.entity.CardStatus;
import com.example.bank_rest.entity.User;
import com.example.bank_rest.exception.NotEnoughBalanceForTransactionException;
import com.example.bank_rest.exception.UserNotFoundException;
import com.example.bank_rest.exception.UserNotHaveCardWithNumberException;
import com.example.bank_rest.repository.CardRepository;
import com.example.bank_rest.security.jwt.provider.JwtProvider;
import com.example.bank_rest.service.CardService;
import com.example.bank_rest.service.UserService;
import com.example.bank_rest.util.Encryption;
import com.example.bank_rest.util.Masking;
import java.math.BigDecimal;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.util.Calendar;
import java.util.List;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

@ExtendWith(MockitoExtension.class)
public class CardServiceTest {
  @Mock
  private UserService userService;

  @Mock
  private CardRepository cardRepository;

  @Mock
  private Encryption encryption;

  @Mock
  private ApplicationEventPublisher applicationEventPublisher;

  @Mock
  private JwtProvider jwtProvider;

  @InjectMocks
  private CardService cardService;

  private static User zinur;

  private static Card testedCard;

  private static Calendar now;

  @BeforeAll
  public static void initUser() {
    zinur = new User("Zinur", "password");
    now = Calendar.getInstance();
    testedCard = new Card(zinur, now.get(Calendar.MONTH), now.get(Calendar.YEAR), CardStatus.ACTIVE, new BigDecimal(0));
  }

  @Test
  public void createCardTest() {
    Mockito.doReturn(zinur).when(userService).findById(1L);
    Mockito.doReturn(testedCard).when(cardRepository).save(Mockito.any());
    try (MockedStatic<Masking> mockedStatic = Mockito.mockStatic(Masking.class)){
      mockedStatic.when(() -> Masking.maskingNumber(Mockito.any())).thenReturn("************4322");
      var cardCreated = cardService.createCard(1L);
      Assertions.assertEquals(zinur, cardCreated.getOwner());
      Mockito.doThrow(UserNotFoundException.class).when(userService).findById(2L);
      Assertions.assertThrows(UserNotFoundException.class, () -> cardService.createCard(2L));
    }
  }

  @Test
  public void blockCardTest() {
    testedCard.setStatus(CardStatus.ACTIVE);
    zinur.getCardList().add(testedCard);
    Mockito.doReturn(zinur).when(userService).findById(1L);
    try (MockedStatic<Masking> mockedStatic = Mockito.mockStatic(Masking.class)) {
      mockedStatic.when(() -> Masking.maskingNumber(Mockito.any())).thenReturn("************4322");
      var blockedCard = cardService.blockCard(1L, "4322");
      Assertions.assertEquals(CardStatus.BLOCKED, blockedCard.getStatus());
      Mockito.doThrow(UserNotFoundException.class).when(userService).findById(2L);
      Assertions.assertThrows(UserNotFoundException.class, () -> cardService.blockCard(2L, "4322"));
    }
  }

  @Test
  public void activeCardTest() {
    Mockito.doReturn(zinur).when(userService).findById(1L);
    try (MockedStatic<Masking> mockedStatic = Mockito.mockStatic(Masking.class)) {
      mockedStatic.when(() -> Masking.maskingNumber(Mockito.any())).thenReturn("************4322");
      var activeCard = cardService.activeCard(1L, "4322");
      Assertions.assertEquals(CardStatus.ACTIVE, activeCard.getStatus());
      Mockito.doThrow(UserNotFoundException.class).when(userService).findById(2L);
      Assertions.assertThrows(UserNotFoundException.class, () -> cardService.blockCard(2L, "4322"));
    }
  }

  @Test
  public void deleteCardTest() {
    Mockito.doReturn(zinur).when(userService).findById(1L);
    zinur.getCardList().add(testedCard);
    try (MockedStatic<Masking> mockedStatic = Mockito.mockStatic(Masking.class)) {
      mockedStatic.when(() -> Masking.maskingNumber(Mockito.any())).thenReturn("************4322");
      cardService.deleteCard(1L, "4322");
      Assertions.assertTrue(zinur.getCardList().isEmpty());
      Mockito.doThrow(UserNotFoundException.class).when(userService).findById(2L);
      Assertions.assertThrows(UserNotFoundException.class, () -> cardService.deleteCard(2L, "4322"));
    }
  }

  @Test
  public void getAllCardsTest() {
    Mockito.doReturn(List.of(testedCard)).when(cardRepository).findAll();
    try (MockedStatic<Masking> mockedStatic = Mockito.mockStatic(Masking.class)) {
      mockedStatic.when(() -> Masking.maskingNumber(Mockito.any())).thenReturn("************4322");
      Assertions.assertEquals(1, cardService.getAllCards().size());
      Mockito.doReturn(List.of()).when(cardRepository).findAll();
      Assertions.assertEquals(0, cardService.getAllCards().size());
    }
  }

  @Test
  public void getAllUserCardsTest() {
    Mockito.doReturn("Zinur").when(jwtProvider).getUsername(Mockito.any());
    zinur.setId(1L);
    Mockito.doReturn(zinur).when(userService).findByUsername("Zinur");
    Mockito.doReturn(List.of(testedCard)).when(cardRepository).findAllByOwnerId(1L);
    try (MockedStatic<Masking> mockedStatic = Mockito.mockStatic(Masking.class)) {
      mockedStatic.when(() -> Masking.maskingNumber(Mockito.any())).thenReturn("************4322");
      Assertions.assertEquals(1, cardService.getAllUserCards("Authorization").size());
      Mockito.doThrow(UserNotFoundException.class).when(userService).findByUsername("Zinur");
      Assertions.assertThrows(UserNotFoundException.class, () -> cardService.getAllUserCards("Authorization"));
      Assertions.assertThrows(IndexOutOfBoundsException.class, () -> cardService.getAllUserCards("Token"));
    }
  }

  @Test
  public void getCardByNumberTest() {
    Mockito.doReturn("Zinur").when(jwtProvider).getUsername(Mockito.any());
    Mockito.doReturn(zinur).when(userService).findByUsername("Zinur");
    Mockito.doReturn(zinur).when(userService).findById(1L);
    try (MockedStatic<Masking> mockedStatic = Mockito.mockStatic(Masking.class)) {
      mockedStatic.when(() -> Masking.maskingNumber(Mockito.any())).thenReturn("************4322");
      Assertions.assertEquals(1, cardService.getCardByNumber("Authorization", "4322").size());
      Assertions.assertThrows(UserNotHaveCardWithNumberException.class, () -> cardService.getCardByNumber("Authorization", "5322"));
      Assertions.assertThrows(IndexOutOfBoundsException.class, () -> cardService.getCardByNumber("Token", "4322"));
      Mockito.doThrow(UserNotFoundException.class).when(userService).findById(1L);
      Assertions.assertThrows(UserNotFoundException.class, () -> cardService.getCardByNumber("Authorization", "4322"));
    }
  }

  @Test
  public void getPageableCardsTest() {
    Mockito.doReturn("Zinur").when(jwtProvider).getUsername(Mockito.any());
    Mockito.doReturn(zinur).when(userService).findByUsername("Zinur");
    try (MockedStatic<Masking> mockedStatic = Mockito.mockStatic(Masking.class)) {
      mockedStatic.when(() -> Masking.maskingNumber(Mockito.any())).thenReturn("************4322");
      Assertions.assertEquals(10, cardService.getPageableCards("Authorization", 0, 10).getSize());
      Assertions.assertThrows(IndexOutOfBoundsException.class, () -> cardService.getPageableCards("Token", 0, 10));
      Mockito.doThrow(UserNotFoundException.class).when(userService).findByUsername("Zinur");
      Assertions.assertThrows(UserNotFoundException.class, () -> cardService.getPageableCards("Authorization", 0, 10));
    }
  }

  @Test
  public void publishBlockTest() {
    zinur.getCardList().get(0).setStatus(CardStatus.ACTIVE);
    Mockito.doReturn("Zinur").when(jwtProvider).getUsername(Mockito.any());
    Mockito.doReturn(zinur).when(userService).findByUsername("Zinur");
    zinur.setId(1L);
    Mockito.doReturn(zinur).when(userService).findById(1L);
    try (MockedStatic<Masking> mockedStatic = Mockito.mockStatic(Masking.class)) {
      mockedStatic.when(() -> Masking.maskingNumber(Mockito.any())).thenReturn("************4322");
      Mockito.doAnswer(invocationOnMock -> {
        zinur.getCardList().get(0).setStatus(CardStatus.BLOCKED);
        return null;
      }).when(applicationEventPublisher).publishEvent(Mockito.any());
      cardService.publishBlock("Authorization", "4322");
      Assertions.assertEquals(CardStatus.BLOCKED, zinur.getCardList().get(0).getStatus());
      Assertions.assertThrows(UserNotHaveCardWithNumberException.class, () -> cardService.publishBlock("Authorization", "5322"));
    }
  }
  @Test
  public void transferTest() {
    Card cardFrom = new Card(zinur, now.get(Calendar.MONTH), now.get(Calendar.YEAR),CardStatus.ACTIVE,new BigDecimal(100));
    cardFrom.setNumber("************5322");
    testedCard.setNumber("************4322");
    zinur.getCardList().add(cardFrom);
    zinur.getCardList().add(testedCard);
    Mockito.doReturn("Zinur").when(jwtProvider).getUsername(Mockito.any());
    Mockito.doReturn(zinur).when(userService).findByUsername("Zinur");
    zinur.setId(1L);
    Mockito.doReturn(zinur).when(userService).findById(1L);
    try (MockedStatic<Masking> mockedStatic = Mockito.mockStatic(Masking.class)) {
      Mockito.doReturn("4322").when(encryption).decryptCardNumber("************4322");
      Mockito.doReturn("5322").when(encryption).decryptCardNumber("************5322");
      mockedStatic.when(() -> Masking.maskingNumber("4322")).thenReturn("************4322");
      mockedStatic.when(() -> Masking.maskingNumber("5322")).thenReturn("************5322");
      var cards = cardService.transfer("Authorization", "5322", "4322",new BigDecimal(35));
      Assertions.assertEquals(new BigDecimal(65), cards.get(0).getBalance());
      Assertions.assertEquals(new BigDecimal(35), cards.get(1).getBalance());
      Assertions.assertThrows(UserNotHaveCardWithNumberException.class, () -> cardService.transfer("Authorization", "6322", "4322", new BigDecimal(5)));
      Assertions.assertThrows(UserNotHaveCardWithNumberException.class, () -> cardService.transfer("Authorization", "5322", "7322", new BigDecimal(5)));
      Assertions.assertThrows(NotEnoughBalanceForTransactionException.class, () -> cardService.transfer("Authorization", "5322", "4322",new BigDecimal(135)));
      zinur.getCardList().clear();
    } catch (InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException |
             InvalidKeyException e) {
      throw new RuntimeException(e);
    }
  }

  public void checkBalanceTest() {
    testedCard.setBalance(new BigDecimal(322));
    Mockito.doReturn(zinur).when(userService).findByUsername("Zinur");
    Mockito.doReturn(zinur).when(userService).findById(1L);
    zinur.getCardList().add(testedCard);
    try (MockedStatic<Masking> mockedStatic = Mockito.mockStatic(Masking.class)) {
      mockedStatic.when(() -> Masking.maskingNumber(Mockito.any())).thenReturn("************4322");
      Assertions.assertEquals(new BigDecimal(322), cardService.checkBalance("Authorization", "4322").getBalance());
    }
  }
}
