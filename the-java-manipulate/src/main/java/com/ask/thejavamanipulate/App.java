package com.ask.thejavamanipulate;

import com.ask.thejavamanipulate.agent.Moja;
import java.io.IOException;

public class App {

  public static void main(String[] args) throws IOException {
    ClassLoader classLoader = App.class.getClassLoader();
    System.out.println("classLoader = " + classLoader);
    System.out.println("classLoader.getParent() = " + classLoader.getParent());

    System.out.println("pullOut : " + new Moja().pullOut());
    System.out.println("pullOutAgent : " +  new Moja().pullOutAgent());
  }
}
