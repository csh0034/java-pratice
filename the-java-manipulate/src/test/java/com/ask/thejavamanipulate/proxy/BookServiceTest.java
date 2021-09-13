package com.ask.thejavamanipulate.proxy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BookServiceTest {

  BookService bookService = new BookServiceProxy(new BookServiceImpl());

  @DisplayName("프록시 메서드 호출")
  @Test
  void proxy() {
    bookService.print(new Book("BookName"));
  }
}