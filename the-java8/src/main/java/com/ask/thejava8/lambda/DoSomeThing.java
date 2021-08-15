package com.ask.thejava8.lambda;

/**
 * 함수형 인터페이스 (Functional Interface)
 * 추상 메소드를 딱 하나만 가지고 있는 인터페이스
 * SAM (Single Abstract Method) 인터페이스
 * @FunctionalInterface 애노테이션을 가지고 있는 인터페이스
 */
@FunctionalInterface
public interface DoSomeThing {

  void print(String text);
}
