package com.ask.thejavamanipulate;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

class StudyTest {

  @Test
  void test() {
    Study study = new Study();
    study.limit = 100;
    study.current = 10;
    System.out.println(study);
    assertFalse(study.isFull());
  }
}