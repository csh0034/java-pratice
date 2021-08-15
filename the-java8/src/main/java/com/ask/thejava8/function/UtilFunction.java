package com.ask.thejava8.function;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class UtilFunction {

  public static void main(String[] args) {
    Function<Integer, Integer> plus10 = i -> i + 10;
    Function<Integer, Integer> multiply2 = i -> i * 2;

    System.out.println(plus10.apply(3));

    // multiply2 실행후 결과값으로 plus10 실행
    System.out.println(plus10.compose(multiply2).apply(3));

    // plus10 실행후 결과값으로 multiply2 실행
    System.out.println(plus10.andThen(multiply2).apply(3));

    BiFunction<Integer, String, String> biFunction = (i, s) -> i + " : " + s;
    System.out.println("biFunction.apply(10, \"Hello\") = " + biFunction.apply(10, "Hello"));

    // 리턴 없이 실행 만함
    Consumer<String> consumer = param -> System.out.println("param = " + param);
    consumer.accept("Hello");

    Supplier<String> supplier = () -> "supplier";
    System.out.println("supplier.get() = " + supplier.get());

    Predicate<Integer> isEven = i -> i % 2 == 0;
    Predicate<Integer> gt10 = i -> i > 10;
    System.out.println("isEven.test(4) = " + isEven.test(4));
    System.out.println("isEven.and(gt10).test(12) = " + isEven.and(gt10).test(12));
    System.out.println("isEven.and(gt10).test(9) = " + isEven.and(gt10).test(11));

    // Function interface 에서 입력과 출력 타입이 같을 경우 사용 가능
    UnaryOperator<Integer> plus20 = i -> i + 20;
    System.out.println("plus20.apply(10) = " + plus20.apply(10));

    // BiFunction interface 에서 두개의 입력과 출력 타입이 같을 경우 사용 가능
    BinaryOperator<String> binaryOperator = (s1, s2) -> s1 + " : " + s2;
    System.out.println("binaryOperator.apply(\"s1\", \"s2\") = " + binaryOperator.apply("s1", "s2"));
  }
}
