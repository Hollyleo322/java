package org.example.math;

import java.util.List;

public class MathOperator {

  public Integer minInt(List<Integer> list) {
    return list.stream().min(Integer::compare).get();
  }

  public Integer maxInt(List<Integer> list) {
    return list.stream().max(Integer::compare).get();
  }

  public Integer sumInt(List<Integer> list) {
    return list.stream().mapToInt(i -> i).sum();
  }

  public Double avgInt(List<Integer> list) {
    return (double) list.stream().mapToInt(i -> i).sum() / list.size();
  }

  public Double minDouble(List<Double> list) {
    return list.stream().min(Double::compare).get();
  }

  public Double maxDouble(List<Double> list) {
    return list.stream().max(Double::compare).get();
  }

  public Double sumDouble(List<Double> list) {
    return list.stream().mapToDouble(i -> i).sum();
  }

  public Double avgDouble(List<Double> list) {
    return list.stream().mapToDouble(i -> i).sum() / list.size();
  }

  public Integer sizeOfShortStr(List<String> list) {
    return list.stream().mapToInt(String::length).min().getAsInt();
  }

  public Integer sizeOfLongStr(List<String> list) {
    return list.stream().mapToInt(String::length).max().getAsInt();
  }
}
