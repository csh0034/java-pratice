package com.ask.thejavatest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class StudyTest {

  @Test
  void create() {
    Study study = new Study();
    assertThat(study).isNotNull();
  }
}