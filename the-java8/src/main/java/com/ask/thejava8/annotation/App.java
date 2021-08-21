package com.ask.thejava8.annotation;

import java.util.Arrays;

/**
 * 자바 8부터 애노테이션을 타입 선언부에도 사용할 수 있게 됨.
 * 자바 8부터 애노테이션을 중복해서 사용할 수 있게 됨.
 */
@Chicken("chicken1")
@Chicken("chicken2")
public class App {

  public static void main(@Chicken String[] args) throws @Chicken RuntimeException {
    // List<@Chicken String> chickens = Arrays.asList("chicken");

    Chicken[] chickens = App.class.getAnnotationsByType(Chicken.class);
    Arrays.stream(chickens).forEach(c -> System.out.println("c.value() = " + c.value()));

    ChickenContainer chickenContainer = App.class.getAnnotation(ChickenContainer.class);
    Chicken[] chickens2 = chickenContainer.value();
    Arrays.stream(chickens2).forEach(c -> System.out.println("ChickenContainer = " + c.value()));
  }

  public static class TestClass<@Chicken T> {
    public static <@Chicken C> void print(C c) {
      System.out.println(c);
    }
  }
}
