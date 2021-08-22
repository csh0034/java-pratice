package com.ask.thejavatest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

/**
 * @BeforeAll / @AfterAll   : required static method
 * @BeforeEach / @AfterEach : non static method
 * @Disabled
 * @DisplayNameGeneration Method 와 Class 레퍼런스를 사용해서 테스트 이름을 표기하는 방법 설정
 * @DisplayName : 테스트이름 직접 입력 가능
 */
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

  @Test
  @DisplayName("스터디 객체 만들기")
  void create1() {
    Study study = new Study();
    assertThat(study).isNotNull();
    System.out.println("create1");
  }

  @Disabled
  @Test
  void create2() {
    Study study = new Study();
    assertThat(study).isNotNull();
    System.out.println("create2");
  }

  @Disabled
  @Test
  void create3_skip_test() {
    Study study = new Study();
    assertThat(study).isNotNull();
    System.out.println("create3");
  }

  @BeforeAll
  static void beforeAll() {
    System.out.println("beforeAll");
  }

  @AfterAll
  static void afterAll() {
    System.out.println("afterAll");
  }

  @BeforeEach
  void beforeEach() {
    System.out.println("beforeEach");
  }

  @AfterEach
  void afterEach() {
    System.out.println("afterEach");
  }
}