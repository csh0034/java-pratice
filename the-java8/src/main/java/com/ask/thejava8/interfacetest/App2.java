package com.ask.thejava8.interfacetest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Spliterator;

/**
 * 자바 8 API 의 기본 메소드와 스태틱 메소드
 */
public class App2 {

  public static void main(String[] args) {
    List<String> names = new ArrayList<>();
    names.add("bb");
    names.add("cc");
    names.add("aa");
    names.add("dddd");

    // forEach()
    System.out.println("=== forEach() ===");
    names.forEach(System.out::println);

    // spliterator()
    System.out.println("=== spliterator() ===");
    Spliterator<String> spliterator = names.spliterator();
    while (spliterator.tryAdvance(System.out::println));

    // spliterator() trySplit()
    System.out.println("=== spliterator() trySplit() ===");
    Spliterator<String> spliterator1 = names.spliterator();
    Spliterator<String> spliterator2 = spliterator1.trySplit();
    while (spliterator1.tryAdvance(System.out::println));
    System.out.println("--");
    while (spliterator2.tryAdvance(System.out::println));

    // stream()
    System.out.println("=== stream() ===");
    names.stream()
        .map(String::toUpperCase)
        .forEach(System.out::println);

    // removeIf()
    System.out.println("=== removeIf() ===");
    names.removeIf(name -> name.length() > 3);
    names.forEach(System.out::println);

    // Comparator()
    System.out.println("=== Comparator.reverseOrder() ===");
    names.sort(Comparator.reverseOrder());
    names.forEach(System.out::println);

    System.out.println("=== Comparator() sort ===");
    names.sort(String::compareTo);
    names.forEach(System.out::println);

    System.out.println("=== Comparator() sort reverse ===");
    Comparator<String> stringComparator = String::compareTo;
    names.sort(stringComparator.reversed());
    names.forEach(System.out::println);
  }
}
