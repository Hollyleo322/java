package com.hollyleo.api_gateway.security.details.service;

import com.hollyleo.api_gateway.exception.UserNotFoundException;
import com.hollyleo.api_gateway.repository.UserRepository;
import com.hollyleo.api_gateway.security.details.MyUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByUsername(username).map(MyUserDetails::new).orElseThrow(() -> new UserNotFoundException("User not found"));
  }
}
