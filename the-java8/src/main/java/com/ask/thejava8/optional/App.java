package com.ask.thejava8.optional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 리턴값으로만 쓰기를 권장한다. (메소드 매개변수 타입, 맵의 키 타입, 인스턴스 필드 타입으로 쓰지 말자.)
 * Optional 을 리턴하는 메소드에서 null 을 리턴하지 말자.
 * 프리미티브 타입용 Optional 은 따로 있다. OptionalInt, OptionalLong,...
 * Collection, Map, Stream Array, Optional 은 Optional 로 감싸지 말 것.
 */
public class App {

  public static void main(String[] args) {
    // NPE
//     OnlineClass onlineClass = new OnlineClass(1, "spring boot", false);
//     Duration studyDuration = onlineClass.getProgress().getStudyDuration();

    List<OnlineClass> springClasses = new ArrayList<>();
    springClasses.add(new OnlineClass(4, "spring core", false));
    springClasses.add(new OnlineClass(5, "rest api development", false));

    Optional<OnlineClass> optional = springClasses.stream()
        .filter(oc -> oc.getTitle().startsWith("spring"))
        .findFirst();

    // Optional 값 있는지 없는지 체크
    boolean isEmpty = optional.isEmpty();
    boolean isPresent = optional.isPresent();
    System.out.println("isEmpty = " + isEmpty);
    System.out.println("isPresent = " + isPresent);

    // Optional 에 있는 값 가져오기
    // 없을 경우 NoSuchElementException
    // OnlineClass onlineClass = spring.get();

    // Optional 있을 경우 처리
    optional.ifPresent((oc) -> System.out.println("oc = " + oc));

    // Optional 에 값이 있으면 가져오고 없는 경우에 ~~를 리턴하라.
    // orElse 일 경우 무조건 실행함
    OnlineClass onlineClass = optional.orElse(createNewClass());
    System.out.println("onlineClass.getTitle() = " + onlineClass.getTitle());

    // Optional 비어있을 경우에만 Supplier 를 실행
    OnlineClass onlineClass2 = optional.orElseGet(App::createNewClass);

    // Optional 비어있을경우 Exception
    optional.orElseThrow(IllegalStateException::new);

    // Optional 들어있는 값 걸러내기
    Optional<OnlineClass> optionalOnlineClass = optional.filter(oc -> oc.getId() > 10);
    System.out.println("optionalOnlineClass.isEmpty() = " + optionalOnlineClass.isEmpty());

    // Optional 들어있는 값 변경
    Optional<String> titleOptional = optional.map(OnlineClass::getTitle);
    titleOptional.ifPresent(System.out::println);

    // Optional 안에 인스턴스가 Optional 인 경우
    System.out.println("optional.map(OnlineClass::getProgress); = " + optional.map(OnlineClass::getProgress));
    System.out.println("optional.flatMap(OnlineClass::getProgress) = " + optional.flatMap(OnlineClass::getProgress));
  }

  private static OnlineClass createNewClass() {
    System.out.println("creating new online class");
    return new OnlineClass(10, "New class", false);
  }
}
