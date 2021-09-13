package com.ask.thejavamanipulate.proxy;

import static net.bytebuddy.matcher.ElementMatchers.any;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BookServiceTest {

  @DisplayName("프록시 메서드 호출")
  @Test
  void proxy() {
    BookService bookService = new BookServiceProxy(new BookServiceImpl());

    bookService.print(new Book("BookName"));
  }

  @DisplayName("jdk dynamic proxy 메서드 호출")
  @Test
  void proxy2() {
    BookService bookService = (BookService) Proxy.newProxyInstance(BookService.class.getClassLoader(), new Class[]{BookService.class},
        new InvocationHandler() {
          private final BookService bookService = new BookServiceImpl();
          @Override
          public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("jdk dynamic proxy");
            return method.invoke(bookService, args);
          }
        });

    bookService.print(new Book("BookName"));
  }

  @DisplayName("CGlib proxy 메서드 호출")
  @Test
  void proxy3() {
    MethodInterceptor handler = new MethodInterceptor() {
      private final BookServiceImpl bookServiceImpl = new BookServiceImpl();
      @Override
      public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("CGlib proxy");
        return method.invoke(bookServiceImpl, objects);
      }
    };

    BookServiceImpl bookServiceImpl = (BookServiceImpl) Enhancer.create(BookServiceImpl.class, handler);
    bookServiceImpl.print(new Book("BookName"));
  }

  @DisplayName("ByteBuddy proxy 메서드 호출")
  @Test
  void proxy4() throws Exception {
    Class<? extends BookServiceImpl> proxyClass = new ByteBuddy().subclass(BookServiceImpl.class)
        .method(any()).intercept(InvocationHandlerAdapter.of(new InvocationHandler() {
          private final BookServiceImpl bookServiceImpl = new BookServiceImpl();

          @Override
          public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("ByteBuddy proxy");
            return method.invoke(bookServiceImpl, args);
          }
        }))
        .make().load(BookServiceImpl.class.getClassLoader()).getLoaded();

    BookServiceImpl bookServiceImpl = proxyClass.getConstructor().newInstance();
    bookServiceImpl.print(new Book("BookName"));
  }
}