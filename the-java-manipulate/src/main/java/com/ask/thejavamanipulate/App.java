package com.ask.thejavamanipulate;

public class App {

  public static void main(String[] args) {
    ClassLoader classLoader = App.class.getClassLoader();
    System.out.println("classLoader = " + classLoader);
    System.out.println("classLoader.getParent() = " + classLoader.getParent());
  }
}
