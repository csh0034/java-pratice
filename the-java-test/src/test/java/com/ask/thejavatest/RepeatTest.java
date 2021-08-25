package com.ask.thejavatest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class RepeatTest {

  private static final String TEST_NAME = "{index}. {displayName}, message={0}";

  @DisplayName("테스트 반복, RepeatedTest")
  @RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetitions}")
  void test(RepetitionInfo info) {
    System.out.println("repeat " + info.getCurrentRepetition() + "/" + info.getTotalRepetitions());
  }

  @DisplayName("파라미터 반복 1 : @EmptySource, @NullSource")
  @ParameterizedTest(name = TEST_NAME)
  @EmptySource
  @NullSource
  // @NullAndEmptySource
  void test1(String message) {
    System.out.println(message);
  }

  @DisplayName("파라미터 반복 2 : @ValueSource")
  @ParameterizedTest(name = TEST_NAME)
  @ValueSource(ints = {10, 20 ,40})
  void test2(int integer) {
    System.out.println(integer);
  }

  @DisplayName("파라미터 반복 3 : @ValueSource, @ConvertWith")
  @ParameterizedTest(name = TEST_NAME)
  @ValueSource(ints = {10, 20 ,40})
  void test3(@ConvertWith(RepeatConverter.class) Repeat repeat) {
    System.out.println(repeat);
  }

  @DisplayName("파라미터 반복 5 : @CsvSource, @AggregateWith")
  @ParameterizedTest(name = TEST_NAME)
  @CsvSource({"10, 스프링", "20, JPA"})
  void test4(@AggregateWith(RepeatAggregator.class) Repeat repeat) {
    System.out.println(repeat);
  }

  // argument 가 하나일 경우
  static class RepeatConverter extends SimpleArgumentConverter {

    @Override
    protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
      assertEquals(Repeat.class, targetType, "Can only convert to Repeat");
      return new Repeat(Integer.parseInt(source.toString()));
    }
  }

  // argument 가 여러개일 경우
  // static inner class or public class 로 만들어야함
  static class RepeatAggregator implements ArgumentsAggregator {

    @Override
    public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context) throws ArgumentsAggregationException {
      return new Repeat(accessor.getInteger(0), accessor.getString(1));
    }
  }
}