package com.example.bank_rest.security.jwt.jwtentity;

import com.example.bank_rest.entity.Role;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class JwtUser {

  private final String username;

  private final List<Role> roleList = new ArrayList<>();
}
