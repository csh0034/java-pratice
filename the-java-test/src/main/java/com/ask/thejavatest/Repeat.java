package com.ask.thejavatest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Repeat {

  private int limit;
  private String name;

  public Repeat(int limit) {
    this.limit = limit;
  }
}
