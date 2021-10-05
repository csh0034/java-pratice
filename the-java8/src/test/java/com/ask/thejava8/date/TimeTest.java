package com.ask.thejava8.date;

import java.time.Duration;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class TimeTest {

  @DisplayName("Variety Duration parse")
  @ParameterizedTest
  @ValueSource(strings = {"PT10S", "PT20M", "PT10H", "P10D", "A"})
  void duration(String durationStr) {
    try {
      Duration duration = Duration.parse(durationStr);
      LocalDateTime now = LocalDateTime.now();

      System.out.println("duration = " + duration);
      System.out.println("LocalDateTime.now() : " + now);
      System.out.println("LocalDateTime.now().minus(duration) : " + now.minus(duration));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}