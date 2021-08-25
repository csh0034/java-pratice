package com.ask.thejavatest;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

/**
 * 기본 전략 : 테스트 메서드 마다 테스트 인스턴스를 새로 만든다.
 * 테스트 메소드를 독립적으로 실행하여 예상치 못한 부작용을 방지하기 위함
 *
 * @TestInstance(Lifecycle.PER_CLASS)
 * 테스트 클래스당 인스턴스를 하나만 만들어 사용한다.
 * 경우에 따라, 테스트 간에 공유하는 모든 상태를 @BeforeEach 또는 @AfterEach 에서 초기화 할 필요가 있다.
 * @BeforeAll 과 @AfterAll 을 인스턴스 메소드 또는 인터페이스에 정의한 default 메소드로 정의할 수도 있다.
 */
@TestInstance(Lifecycle.PER_CLASS)
class InstanceTest {

  int value;

  @Test
  void test1() {
    System.out.println("value = " + ++value);
    System.out.println(this);
  }

  @Test
  void test2() {
    System.out.println("value = " + ++value);
    System.out.println(this);
  }

  @BeforeAll
  void beforeAll() {
    System.out.println("\nbeforeAll\n");
  }

  @AfterAll
  void afterAll() {
    System.out.println("\nafterAll\n");
  }

}