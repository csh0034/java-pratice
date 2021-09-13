package com.ask.thejavamanipulate.proxy;

public class BookServiceProxy implements BookService {

  BookService bookService;

  public BookServiceProxy(BookService bookService) {
    this.bookService = bookService;
  }

  @Override
  public void print(Book book) {
    System.out.println("proxy");
    bookService.print(book);
  }
}
