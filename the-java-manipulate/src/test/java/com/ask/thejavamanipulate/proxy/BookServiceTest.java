package com.ask.thejavamanipulate.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BookServiceTest {

  BookService bookService = new BookServiceProxy(new BookServiceImpl());

  BookService bookService2 = (BookService) Proxy.newProxyInstance(BookService.class.getClassLoader(), new Class[]{BookService.class},
      new InvocationHandler() {
        private final BookService bookService = new BookServiceImpl();
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
          System.out.println("dynamic proxy");
          return method.invoke(bookService, args);
        }
      });

  @DisplayName("프록시 메서드 호출")
  @Test
  void proxy() {
    bookService.print(new Book("BookName"));
  }

  @DisplayName("다이나믹 프록시 메서드 호출")
  @Test
  void proxy2() {
    bookService2.print(new Book("BookName"));
  }
}