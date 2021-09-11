package com.ask.thejavamanipulate.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class App2 {

  public static void main(String[] args)
      throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
    Class<Book> bookClass = Book.class;
    Constructor<Book> constructor = bookClass.getConstructor();
    Book book = constructor.newInstance();

    System.out.println("book = " + book);

    Field b = Book.class.getDeclaredField("B");
    b.setAccessible(true);
    System.out.println("b = " + b.get(null));
    b.set(null, "BBBBBBBBB");
    System.out.println("b = " + b.get(null));

    Method g = Book.class.getDeclaredMethod("g");
    g.invoke(book);

    Method sum = Book.class.getDeclaredMethod("sum", int.class, int.class);
    Object invoke = sum.invoke(book, 1, 2);
    System.out.println("sum : " + invoke);
  }

}

