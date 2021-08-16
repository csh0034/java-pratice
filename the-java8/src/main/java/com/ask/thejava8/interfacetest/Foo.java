package com.ask.thejava8.interfacetest;

/**
 * Default Methods
 * 인터페이스에 메소드 선언이 아니라 구현체를 제공하는 방법
 * 해당 인터페이스를 구현한 클래스를 깨트리지 않고 새 기능을 추가할 수 있다.
 * 기본 메소드는 구현체가 모르게 추가된 기능으로 그만큼 리스크가 있다.
 * 반드시 문서화 할 것. (@implSpec 자바독 태그 사용)
 * Object 가 제공하는 기능 (equals, hasCode)는 기본 메소드로 제공할 수 없다. 구현체가 재정의해야 한다.
 * 본인이 수정할 수 있는 인터페이스에만 기본 메소드를 제공할 수 있다.
 * 인터페이스를 상속받는 인터페이스에서 다시 추상 메소드로 변경할 수 있다.
 * 인터페이스 구현체가 재정의 할 수도 있다.
 * 비침투성, 개발자에게 상속을 강제하지 않을수 있음
 */
public interface Foo {

  void printName();

  String getName();

  /**
   * @implSpec
   * 해당 메서드는 getName()으로 가져온 문자열을 대문자로 바꿔 출력한다.
   */
  default void printNameUpperCase() {
    System.out.println(getName().toUpperCase());
  }

  /**
   * 해당 타입 관련 헬퍼 또는 유틸리티 메서드 제공시 인터페이스에 스태틱 메서드 제공 가능
   */
  static void printAnything() {
    System.out.println("Foo");
  }
}
