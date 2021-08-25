package com.ask.thejavatest;

import org.junit.Before;
import org.junit.Test;

/**
 * junit-vintage-engine 을 의존성으로 추가하면
 * JUnit 5의 junit-platform 으로 JUnit 3과 4로 작성된 테스트를 실행할 수 있다.
 * 패키지 기준으로 실행하면 JUnit Jupiter, JUnit Vintage 분리돼서 결과가 보임
 *
 * JUnit4 -> jUnit5
 * @Category(Class) -> @Tag(String)
 * @RunWith, @Rule, @ClassRule -> @ExtendWith, @RegisterExtension
 * @Ignore -> @Disabled
 * @Before, @After, @BeforeClass, @AfterClass -> @BeforeEach, @AfterEach, @BeforeAll, @AfterAll
 */
public class JUnit4Test {

  @Before
  public void before() {
    System.out.println("before");
  }

  @Test
  public void test1() {
    System.out.println("test1-junit4");
  }

  @Test
  public void test2() {
    System.out.println("test2-junit4");
  }
}
