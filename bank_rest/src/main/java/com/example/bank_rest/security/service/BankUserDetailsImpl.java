package com.example.bank_rest.security.service;

import com.example.bank_rest.exception.UserNotFoundException;
import com.example.bank_rest.repository.UserRepository;
import com.example.bank_rest.security.BankUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankUserDetailsImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByUsername(username).map(BankUserDetails::new)
        .orElseThrow(() -> new UserNotFoundException("User with name " + username + " not found"));
  }
}
