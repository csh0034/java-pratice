package com.ask.thejava8.interfacetest;

public class App {

  public static void main(String[] args) {
    Foo foo = new DefaultFoo("csh");

    foo.printName();

    // default method 호출
    foo.printNameUpperCase();

    // interface static method 호출
    Foo.printAnything();
  }
}
