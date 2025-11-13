package com.hollyleo.api_gateway.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usr")
@NoArgsConstructor
@Getter
@Setter
public class User {
  private String username;
  private String password;
  List<Role> roles = new ArrayList<>();
  public User(String username, String password){
    this.username = username;
    this.password = password;
  }
}
