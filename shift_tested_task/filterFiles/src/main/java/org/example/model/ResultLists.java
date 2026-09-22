package org.example.model;


import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ResultLists {

  private final List<Integer> intNumbers;
  private final List<Double> doubleNumbers;
  private final List<String> strings;
}
