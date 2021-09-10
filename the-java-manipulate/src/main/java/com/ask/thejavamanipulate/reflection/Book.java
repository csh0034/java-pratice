package com.ask.thejavamanipulate.reflection;

public class Book extends AbstractBook implements BookInterfaceA, BookInterfaceB {

  private String a = "a";

  private static String B = "B";

  private static final String C = "C";

  public String d = "d";

  protected String e = "e";

  public Book() {
  }

  public Book(String a, String d, String e) {
    this.a = a;
    this.d = d;
    this.e = e;
  }

  private void f() {
    System.out.println("f");
  }

  public void g() {
    System.out.println("g");
  }

  public int h() {
    return 1;
  }
}
