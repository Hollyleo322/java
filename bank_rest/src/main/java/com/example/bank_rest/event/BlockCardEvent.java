package com.example.bank_rest.event;

import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;
@Getter
@ToString
public class BlockCardEvent extends ApplicationEvent {
  private String number;
  private Long ownerId;
  public BlockCardEvent(Object source, String number, Long ownerId) {
    super(source);
    this.number = number;
    this.ownerId = ownerId;
  }
}
