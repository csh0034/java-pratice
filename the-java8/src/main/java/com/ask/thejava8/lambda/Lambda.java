package com.ask.thejava8.lambda;

import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;

/**
 *  Lambda
 *  (인자 리스트) -> {바디}
 *  인자가 없을 때: ()
 *  인자가 한개일 때: (one) 또는 one
 *  인자가 여러개 일 때: (one, two)
 *  인자의 타입은 생략 가능, 컴파일러가 추론(infer)하지만 명시할 수도 있다. (Integer one, Integer two)
 */
public class Lambda {

  public static void main(String[] args) {

    // 람다 표현식 (Lambda Expressions)
    DoSomeThing doSomeThing = text -> System.out.println("text = " + text);
    doSomeThing.print("Hello");

    IntSupplier intSupplier = () -> Integer.MAX_VALUE;
    System.out.println("intSupplier.getAsInt() = " + intSupplier.getAsInt());

    LongSupplier longSupplier = () -> Long.MAX_VALUE;
    System.out.println("longSupplier.getAsLong() = " + longSupplier.getAsLong());
  }

  // 변수 캡처 (Variable Capture)
  // final 이거나 effective final 일 경우 가능
  private void run() {

    // effective final : 사실상 final 인 변수, 값 변경이 있을 경우 참조 할수 없음
    int baseNumber = 10;

    // 로컬 클래스
    // 새로운 스콥을 만듬
    class LocalClass {
      void printBaseNumber() {
        int baseNumber = 11;
        System.out.println("baseNumber = " + baseNumber);
      }
    }

    // 익명 클래스
    // 새로운 스콥을 만듬
    Consumer<Integer> consumer = new Consumer<Integer>() {

      @Override
      public void accept(Integer integer) {
        int baseNumber = 11;
        System.out.println("baseNumber = " + baseNumber);
      }
    };

    // 람다
    // 새로운 스콥을 만들지 않음
    IntConsumer intConsumer = i -> {
      // 쉐도윙 하지 않는다. 람다는 람다를 감싸고 있는 스콥과 같다.
      // int baseNumber = 11;
      System.out.println("baseNumber = " + baseNumber);
    };
  }
}
