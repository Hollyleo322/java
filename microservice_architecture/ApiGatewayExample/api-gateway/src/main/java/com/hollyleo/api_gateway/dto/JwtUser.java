package com.hollyleo.api_gateway.dto;

import com.hollyleo.api_gateway.entity.Role;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JwtUser {

  private final String username;

  private final List<Role> roles = new ArrayList<>();
}
