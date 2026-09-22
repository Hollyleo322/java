package com.hollyleo.api_gateway.service;

import com.hollyleo.api_gateway.entity.Role;
import com.hollyleo.api_gateway.entity.User;
import com.hollyleo.api_gateway.exception.UserNotFoundException;
import com.hollyleo.api_gateway.exception.UsernameAlreadyExistException;
import com.hollyleo.api_gateway.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;


  public User findById(Long id) {
    log.info("Find user by id {}", id);
    return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
  }
  public User findByUsername(String username) {
    log.info("Find user by username {}", username);
    return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User with name " + username + " not found"));
  }
  public User create(User user) throws UsernameAlreadyExistException {
    if (userRepository.existsByUsername(user.getUsername())) {
      throw new UsernameAlreadyExistException("Username is being used");
    }
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.getRoles().add(Role.ROLE_USER);
    log.info("Create user {}", user);
    return userRepository.save(user);
  }

  public void deleteUserById(Long id) {
    log.info("Delete user with id {}", id);
    userRepository.deleteById(id);
  }

  public List<Role> getRolesByUsername (String username){
    var user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));
    return user.getRoles();
  }
}
