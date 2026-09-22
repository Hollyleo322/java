package com.example.bank_rest.repository;

import com.example.bank_rest.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {

  boolean existsByNumber(String number);

  List<Card> findAllByOwnerId(Long ownerId);
}
