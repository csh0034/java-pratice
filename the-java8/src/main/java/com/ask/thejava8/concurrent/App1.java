package com.ask.thejava8.concurrent;

/**
 * Thread Runnable 활용
 */
public class App1 {

  public static void main(String[] args) {
    Thread thread = new Thread(() -> System.out.println("world : " + Thread.currentThread().getName()));
    thread.start();
    System.out.println("hello : " + Thread.currentThread().getName());
  }
}
