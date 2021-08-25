package com.ask.thejavatest;

import static com.ask.thejavatest.Study.EXCEPTION_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

import java.time.Duration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * @BeforeAll / @AfterAll   : required static method
 * @BeforeEach / @AfterEach : non static method
 * @Disabled
 * @DisplayNameGeneration Method 와 Class 레퍼런스를 사용해서 테스트 이름을 표기하는 방법 설정
 * @DisplayName : 테스트이름 직접 입력 가능
 */
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

  @Test
  @DisplayName("스터디 객체 만들기")
  @Tag("fast")
  void test1() {
    Study study = new Study(3);

    // assertAll 로 감싸면 모든 구문을 확인함
    assertAll(
        () -> assertNotNull(study),
        () -> assertEquals(StudyStatus.DRAFT, study.getStatus(), "스터디를 처음 만들면 상태값이 DRAFT 여야 한다."),
        () -> assertTrue(study.getLimit() > 0, "스터티 최대 참석 가능 인원은 0보다 커야 한다")
    );

    System.out.println("create1");
  }

  @Test
  @DisplayName("assertThrows test")
  @Tag("slow")
  void test2() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Study(-10));
    assertEquals(EXCEPTION_MESSAGE, exception.getMessage());
  }

  @Disabled
  @Test
  @DisplayName("assertTimeout test")
  void test3() {

    // supplier 코드가 끝날때 까지 대기함
    // assertTimeout(Duration.ofSeconds(3), () -> new Study(StudyStatus.STARTED), "스터디 객체는 3초안에 생성되어야 한다");

    // supplier 코드가 Duration 보다 오래 걸릴 경우 종료
    // 테스트는 롤백을 기본으로 하지만 해당 supplier 에서 ThreadLocal 을 사용할 경우 공유가 안되므로 DB에 반영 될수도 있음
    assertTimeoutPreemptively(Duration.ofMillis(100), () -> new Study(StudyStatus.STARTED), "스터디 객체는 3초안에 생성되어야 한다");
  }

  @Test
  @DisplayName("assumeTrue test")
  void test4() {
    assumeTrue("prod".equalsIgnoreCase(System.getProperty("profile")));
    System.out.println("skip");
  }

  @ParameterizedTest
  @ValueSource(strings = {"prod", "local"})
  @DisplayName("assumingThat test")
  void test5(String profile) {
    assumingThat("prod".equalsIgnoreCase(profile), () -> System.out.println("profile : prod"));
    assumingThat("local".equalsIgnoreCase(profile), () -> System.out.println("profile : local"));
  }

  @Test
  @DisplayName("@EnabledOnOs test")
  @EnabledOnOs(OS.MAC)
  void test6() {
    System.out.println("EnabledOnOs OS.MAC");
  }

  @Test
  @DisplayName("@DisabledOS test")
  @DisabledOnOs(OS.WINDOWS)
  void test7() {
    System.out.println("DisabledOnOs OS.WINDOWS");
  }

  @Test
  @DisplayName("@EnabledForJreRange test")
  @EnabledForJreRange(min = JRE.JAVA_8, max = JRE.JAVA_11)
  // @EnabledOnJre(value = {JRE.JAVA_8})
  // @EnabledIfEnvironmentVariable(named = "TEST", matches = "local")
  // @EnabledIfSystemProperty(named = "TEST", matches = "local")
  void test8() {
    System.out.println("EnabledForJreRange JAVA_8 ~ JAVA_11");
  }

  @FastTest
  void test9() {
    System.out.println("FastTest");
  }

  @Disabled
  @Test
  void create_skip_test() {
    Study study = new Study();
    assertThat(study).isNotNull();
  }

  @BeforeAll
  static void beforeAll() {
    System.out.println("\nbeforeAll\n");
  }

  @AfterAll
  static void afterAll() {
    System.out.println("\nafterAll\n");
  }

  @BeforeEach
  void beforeEach() {
    System.out.println("beforeEach");
  }

  @AfterEach
  void afterEach() {
    System.out.println("afterEach");
  }

}