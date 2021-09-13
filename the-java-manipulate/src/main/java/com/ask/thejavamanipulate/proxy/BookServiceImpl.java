package com.ask.thejavamanipulate.proxy;

public class BookServiceImpl implements BookService {

  @Override
  public void print(Book book) {
    System.out.println("book.getName() = " + book.getName());
  }
}
