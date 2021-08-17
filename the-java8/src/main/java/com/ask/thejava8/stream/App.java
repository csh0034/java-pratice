package com.ask.thejava8.stream;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * 데이터를 담고 있는 저장소 (컬렉션)이 아니다.
 * Functional in nature, 스트림이 처리하는 데이터 소스를 변경하지 않는다.
 * 스트림으로 처리하는 데이터는 오직 한번만 처리한다.
 * 무제한일 수도 있다. (Short Circuit 메소드를 사용해서 제한할 수 있다.)
 * 중개 오퍼레이션은 근본적으로 lazy 하다. 종료 오퍼레이션 오기 전까지 수행 안함
 * 손쉽게 병렬 처리할 수 있다.
 * 중개 오퍼레이션 : Stream 을 리턴한다. filter, map, limit, skip, sorted, ...
 * 종료 오퍼레이션 : Stream 을 리턴하지 않는다. collect, allMatch, count, forEach, min, max, ...
 */
public class App {

  public static void main(String[] args) {

    List<OnlineClass> springClasses = new ArrayList<>();
    springClasses.add(new OnlineClass(1, "spring boot", true));
    springClasses.add(new OnlineClass(2, "spring data jpa", true));
    springClasses.add(new OnlineClass(3, "spring mvc", false));
    springClasses.add(new OnlineClass(4, "spring core", false));
    springClasses.add(new OnlineClass(5, "rest api development", false));

    System.out.println("spring 으로 시작하는 수업");
    springClasses.stream()
        .filter(oc -> oc.getTitle().startsWith("spring"))
        .forEach(oc -> System.out.println("oc.getId() = " + oc.getId()));

    System.out.println("close 되지 않은 수업");
    springClasses.stream()
        .filter(Predicate.not(OnlineClass::isClosed))
        .forEach(oc -> System.out.println("oc.getId() = " + oc.getId()));

    System.out.println("수업 이름만 모아서 스트림 만들기");
    springClasses.stream()
        .map(OnlineClass::getTitle)
        .forEach(System.out::println);

    List<OnlineClass> javaClasses = new ArrayList<>();
    javaClasses.add(new OnlineClass(6, "The Java, Test", true));
    javaClasses.add(new OnlineClass(7, "The Java, Code manipulation", true));
    javaClasses.add(new OnlineClass(8, "The Java, 8 to 11", false));

    List<List<OnlineClass>> events = new ArrayList<>();
    events.add(springClasses);
    events.add(javaClasses);

    // flatten
    System.out.println("두 수업 목록에 들어있는 모든 수업 아이디 출력");
    events.stream()
        .flatMap(Collection::stream)
        .forEach(oc -> System.out.println("oc.getId() = " + oc.getId()));

    System.out.println("10부터 1씩 증가하는 무제한 스트림 중에서 앞에 10개 빼고 최대 10개 까지만");
    Stream.iterate(10, i -> i + 1)
      .skip(10)
      .limit(10)
      .forEach(System.out::println);

    System.out.println("자바 수업 중에 Test가 들어있는 수업이 있는지 확인");
    boolean test = javaClasses.stream().anyMatch(oc -> oc.getTitle().contains("Test"));
    System.out.println("test = " + test);

    System.out.println("스프링 수업 중에 제목에 spring이 들어간 것만 모아서 List로 만들기");
    List<String> titles = springClasses.stream()
        .map(OnlineClass::getTitle)
        .filter(title -> title.contains("spring"))
        .collect(toList());

    System.out.println("titles = " + titles);
  }
}
