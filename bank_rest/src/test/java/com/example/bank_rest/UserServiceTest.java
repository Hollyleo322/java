package com.example.bank_rest;


import static com.example.bank_rest.entity.Role.ROLE_USER;

import com.example.bank_rest.entity.User;
import com.example.bank_rest.exception.UserNotFoundException;
import com.example.bank_rest.exception.UsernameAlreadyExistException;
import com.example.bank_rest.repository.UserRepository;
import com.example.bank_rest.service.UserService;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;


  @InjectMocks
  private UserService userService;

  @Test
  public void findByIdTest() {
    Mockito.doReturn(Optional.of(new User("Holly", "password"))).when(userRepository).findById(1L);
    Mockito.doThrow(new UserNotFoundException("User not found")).when(userRepository).findById(322L);
    var user = userService.findById(1L);
    Assertions.assertEquals("Holly", user.getUsername());
    Assertions.assertThrows(UserNotFoundException.class, () -> userService.findById(322L));
  }
  @Test
  public void findByUsernameTest() {
    Mockito.doReturn(Optional.of(new User("Snickers", "password"))).when(userRepository).findByUsername("Snickers");
    Mockito.doThrow(new UserNotFoundException("User not found")).when(userRepository).findByUsername("Mars");
    var user = userService.findByUsername("Snickers");
    Assertions.assertEquals("Snickers", user.getUsername());
    Assertions.assertThrows(UserNotFoundException.class, () -> userRepository.findByUsername("Mars"));
  }
  @Test
  public void createUserTest() {
    var pepsi = new User("Pepsi", "password");
    pepsi.getRoles().add(ROLE_USER);
    Mockito.doReturn(pepsi).when(userRepository).save(pepsi);
    var savedUser = userService.create(pepsi);
    Assertions.assertEquals("Pepsi", savedUser.getUsername());
    Assertions.assertEquals(ROLE_USER, savedUser.getRoles().get(0));
    Mockito.doThrow(UsernameAlreadyExistException.class).when(userRepository).save(pepsi);
    Assertions.assertThrows(UsernameAlreadyExistException.class, () -> userService.create(pepsi));
  }
  @Test
  public void deleteUserTest() {
    userService.deleteUserById(2L);
    Mockito.doThrow(UserNotFoundException.class).when(userRepository).findById(2L);
    Assertions.assertThrows(UserNotFoundException.class, () -> userService.findById(2L));
  }
  @Test
  public void getRolesTest() {
    var cola = new User("Cola", "password");
    cola.getRoles().add(ROLE_USER);
    Mockito.doReturn(Optional.of(cola)).when(userRepository).findByUsername("Cola");
    Assertions.assertEquals(ROLE_USER, userService.getRolesByUsername("Cola").get(0));
  }
}
