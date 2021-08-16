package com.ask.thejava8.lambda;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class MethodReference {

  public static void main(String[] args) {

    // 스태틱 메서드 참조
    // 타입::스태틱 메소드
    UnaryOperator<String> hi = Greeting::hi;
    System.out.println("hi.apply(\"apply\") = " + hi.apply("apply"));

    // 특정 객체 인스턴스 메서드 참조
    // 객체 레퍼런스::인스턴스 메소드
    Greeting greeting = new Greeting();
    UnaryOperator<String> hello = greeting::hello;
    System.out.println("hello.apply(\"apply\") = " + hello.apply("apply"));

    // 임의 객체의 인스턴스 메서드 참조
    // 타입::인스턴스 메소드
    String[] names = {"b", "a", "c"};
    Arrays.sort(names, String::compareToIgnoreCase);
    System.out.println("Arrays.toString(names) = " + Arrays.toString(names));

    // 생성자 참조
    // 타입::new
    Supplier<Greeting> greetingSupplier = Greeting::new;
    System.out.println("greetingSupplier.get() = " + greetingSupplier.get());

    Function<String, Greeting> greetingFunction = Greeting::new;
    System.out.println("greetingFunction.apply(\"abc\") = " + greetingFunction.apply("abc"));
  }

  public static class Greeting {

    private String name;

    public Greeting() {
    }

    public Greeting(String name) {
      this.name = name;
    }

    public String hello(String name) {
      return "hello " + name;
    }

    public static String hi(String name) {
      return "hi " + name;
    }

    @Override
    public String toString() {
      return "Greeting{" +
          "name='" + name + '\'' +
          '}';
    }
  }
}
