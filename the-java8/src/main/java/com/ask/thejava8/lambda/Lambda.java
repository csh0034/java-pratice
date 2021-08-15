package com.ask.thejava8.lambda;

public class Lambda {

  public static void main(String[] args) {

    // 람다 표현식 (Lambda Expressions)
    DoSomeThing doSomeThing = text -> System.out.println("text = " + text);
    doSomeThing.print("Hello");
  }
}
