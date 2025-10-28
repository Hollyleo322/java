package com.example.bank_rest.event.listener;

import com.example.bank_rest.event.BlockCardEvent;
import com.example.bank_rest.service.CardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class BlockCardListener {

  private final CardService cardService;

  @EventListener
  public void onEvent(BlockCardEvent event) {
    log.info("Block card {}",event );
    cardService.blockCard(event.getOwnerId(), event.getNumber());
  }
}
