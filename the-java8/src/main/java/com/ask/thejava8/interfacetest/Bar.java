package com.ask.thejava8.interfacetest;

public interface Bar extends Foo {

  /**
   * 인터페이스를 상속받는 인터페이스에서 다시 추상 메소드로 변경 가능
   */
  void printNameUpperCase();
}
