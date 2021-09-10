package com.ask.thejavamanipulate.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class App {

  public static void main(String[] args) throws Exception {
    Class<Book> bookClass = Book.class;

    Book book = new Book();
    Class<? extends Book> bookClass2 = book.getClass();
    Class<?> bookClass3 = Class.forName("com.ask.thejavamanipulate.reflection.Book");

    System.out.println("bookClass = " + bookClass);
    System.out.println("bookClass2 = " + bookClass2);
    System.out.println("bookClass3 = " + bookClass3);
    System.out.println("");

    // 필드 출력
    Arrays.stream(bookClass.getDeclaredFields())
        .forEach(System.out::println);
    System.out.println("");

    // 필드 값 출력
    Arrays.stream(bookClass.getDeclaredFields())
        .forEach(f -> {
          try {
            f.setAccessible(true);
            System.out.printf("%s : %s\n", f, f.get(book));
          } catch (IllegalAccessException e) {
            e.printStackTrace();
          }
        });
    System.out.println("");

    // 생성자 출력
    Arrays.stream(bookClass.getDeclaredConstructors())
        .forEach(c -> {
          c.setAccessible(true);
          System.out.printf("%s\n", c);
        });
    System.out.println("");

    // 메서드 출력
    Arrays.stream(bookClass.getDeclaredMethods())
        .forEach(m -> {
          try {
            m.setAccessible(true);
            System.out.printf("%s : %s\n", m, m.invoke(book));
          } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
          }
        });
    System.out.println("");
    
    // 상속, 구현 인터페이스
    System.out.println("bookClass.getSuperclass() = " + bookClass.getSuperclass());
    Arrays.stream(bookClass.getInterfaces()).forEach(i -> System.out.println(i.getName()));
    System.out.println("");

    // 필드 정보 출력
    Arrays.stream(bookClass.getDeclaredFields())
        .forEach(f -> {
          int modifiers = f.getModifiers();
          System.out.println(f);
          System.out.println("isFinal = " + Modifier.isFinal(modifiers));
          System.out.println("isPublic = " + Modifier.isPublic(modifiers));
          System.out.println("isPrivate = " + Modifier.isPrivate(modifiers));
          System.out.println("");
        });

    // annotation 정보 출력
    Arrays.stream(bookClass.getAnnotations())
        .peek(annotation -> {
          if (annotation instanceof BookAnnotation) {
            BookAnnotation bookAnnotation = (BookAnnotation) annotation;
            System.out.println("number() = " + bookAnnotation.number());
            System.out.println("name() = " + bookAnnotation.name());
            System.out.println("value() = " + bookAnnotation.value());
          }
        })
        .forEach(System.out::println);


    System.out.println("");
  }
  
}
