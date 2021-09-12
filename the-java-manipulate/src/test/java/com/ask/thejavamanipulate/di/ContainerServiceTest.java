package com.ask.thejavamanipulate.di;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ContainerServiceTest {

  @DisplayName("BookRepository 생성")
  @Test
  void getObject1() {
    BookRepository bookRepository = ContainerService.getObject(BookRepository.class);
    assertNotNull(bookRepository);
  }

  @DisplayName("BookService 생성")
  @Test
  void getObject2() {
    BookService bookService = ContainerService.getObject(BookService.class);
    assertNotNull(bookService);
    assertNotNull(bookService.bookRepository);
  }

}