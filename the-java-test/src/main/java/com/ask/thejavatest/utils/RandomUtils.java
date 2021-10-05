package com.ask.thejavatest.utils;

public final class RandomUtils {

  public static int randomInt(int start) {
    return (int)(Math.random() * start + 101);
  }
}
