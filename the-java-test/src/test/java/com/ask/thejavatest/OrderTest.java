package com.ask.thejavatest;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * 메서드당 단위테스트가 독립적으로 실행이 가능해야함 서로간 의존성이 없어야 한다
 * 하지만 통합, 시나리오 테스트 등 순서대로 실행을 해야할 경우 지정 가능
 * @TestMethodOrder 가 선언되어있지 않다면 기본 알고리즘에 의해 순서가 정해지지만 아닌 경우도 있다
 * @TestInstance(value = PER_CLASS) 와 같이 사용하여 상태를 공유하면 순서를 유지할 수도 있고
 * @TestMethodOrder 만 사용해도 된다
 */
// @TestMethodOrder(MethodOrderer.Random.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderTest {

  @Test
  @Order(1)
  void a() {
    System.out.println("a");
  }

  @Test
  @Order(2)
  void c() {
    System.out.println("c");
  }

  @Test
  @Order(3)
  void d() {
    System.out.println("d");
  }
}