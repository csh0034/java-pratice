package com.ask.thejavamanipulate;

public class Study {

  int limit;

  int current;

  public boolean isFull() {
    if (limit == 0) {
      return false;
    }

    if (current < limit) {
      return false;
    }

    return true;
  }
}
