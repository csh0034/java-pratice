package com.ask.thejavatest.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

@DisplayName("static mock test")
@Slf4j
class RandomUtilsTest {

  private static MockedStatic<RandomUtils> mRandomUtils;

  @BeforeAll
  static void beforeAll() {
    mRandomUtils = Mockito.mockStatic(RandomUtils.class);
  }

  @AfterAll
  static void afterAll() {
    mRandomUtils.close();
  }

  @DisplayName("beforeAll, afterAll 활용")
  @Test
  void staticMockito1() {
    // given
    int start = 20;
    int returnValue = 1000;

    given(RandomUtils.randomInt(start)).willReturn(returnValue);

    // when
    int randomInt1 = RandomUtils.randomInt(start);
    int randomInt2 = RandomUtils.randomInt(100);
    int randomInt3 = RandomUtils.randomInt(1000);

    // then
    mRandomUtils.verify(() -> RandomUtils.randomInt(anyInt()), times(3));

    log.info("randomInt1 : {}", randomInt1);
    log.info("randomInt2 : {}", randomInt2);
    log.info("randomInt3 : {}", randomInt3);
  }

  @DisplayName("try with resources (Mock scope) 활용")
  @Test
  void staticMockito2() {

    // before mock scope
    assertThat(StringUtils.returnString("before-val")).startsWith("returnString : before-val");

    try (MockedStatic<StringUtils> mock = Mockito.mockStatic(StringUtils.class)) {
      // given
      String paramValue = "param!!!!";
      String returnValue = "return!!!!";

      mock.when(() -> StringUtils.returnString(paramValue)).thenReturn(returnValue);

      // when
      String returnValue1 = StringUtils.returnString(paramValue);
      String returnValue2 = StringUtils.returnString("A");
      String returnValue3 = StringUtils.returnString("B");

      // then
      mock.verify(() -> StringUtils.returnString(anyString()), times(3));

      log.info("returnValue1 : {}", returnValue1);
      log.info("returnValue2 : {}", returnValue2);
      log.info("returnValue3 : {}", returnValue3);
    }

    // after mock scope
    assertThat(StringUtils.returnString("after-val")).startsWith("returnString : after-val");
  }
}