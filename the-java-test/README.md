# JUnit5 
JUnit은 자바 프로그래밍 언어용 유닛 테스트 프레임워크이다.  
- Platform:  테스트를 실행해주는 런처 제공. TestEngine API 제공.
- Jupiter: TestEngine API 구현체로 JUnit 5를 제공.
- Vintage: JUnit 4와 3을 지원하는 TestEngine 구현체.

## pom.xml
```xml
<!-- 스프링 부트 사용시 -->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-test</artifactId>
  <scope>test</scope>
</dependency>

<!-- 단독으로 사용시 -->
<dependency>
  <groupId>org.junit.jupiter</groupId>
  <artifactId>junit-jupiter-engine</artifactId>
  <version>5.7.2</version>
  <scope>test</scope>
</dependency>

<!-- Mock Static Class Test -->
<dependency>
  <groupId>org.mockito</groupId>
  <artifactId>mockito-inline</artifactId>
  <version>3.9.0</version>
  <scope>test</scope>
</dependency>
```

## JUnit 5 시작하기
**기본 어노테이션**

```java
// JUnit 5 의 경우 클래스 또는 메서드에 public 안써도됨 
class JUnitTest {
  
  @Test // JUnit test 지정
  void test() {
    log.info("test excute");
  }

  @BeforeAll // 가장 먼저 실행, static 이어야함
  static void setup() {
    log.info("@BeforeAll - executes once before all test methods in this class");
  }

  @BeforeEach // 각 테스트 메서드 전에 실행
  void init() {
    log.info("@BeforeEach - executes before each test method in this class");
  }

  @AfterAll // 가장 끝에 실행, static 이어야함
  static void done() {
    log.info("@AfterAll - executed after all test methods.");
  }

  @AfterEach // 각 테스트 메서드 후에 실행
  void tearDown() {
    log.info("@AfterEach - executed after each test method.");
  }

  @Disabled // 테스트 클래스 또는 메서드 비활성화
  @Test
  void test() {
    log.info("test excute");
  }
}
```

## JUnit 5 테스트 이름 표시
```java
// 테스트명 언더스코어를 공백으로 변경
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
class JUnitTest {
  
  @DisplayName("로그 출력") // 테스트 이름 지정 가능
  @Test
  void test() {
    log.info("test excute");
  }
  
  @Test
  void test_underscore() {
    log.info("test excute");
  }
}
```

## JUnit 5 Assertion
매개변수로 Supplier<String> 타입의 인스턴스를 람다 형태로 제공할 수 있다.  
복잡한 메시지 생성해야 하는 경우 사용하면 실패한 경우에만 해당 메시지를 만들게 할 수 있다.


| 설명   | 메서드 |
|-------|------|
| 실제 값이 기대한 값과 같은지 확인 |  assertEqulas(expected, actual) |
| 값이 null이 아닌지 확인 | assertNotNull(actual) |
| 다음 조건이 참(true)인지 확인 | assertTrue(boolean) |
| 모든 확인 구문 확인 |  assertAll(executables...) |
| 예외 발생 확인 |  assertThrows(expectedType, executable) |
| 특정 시간 안에 실행이 완료되는지 확인 |  assertTimeout(duration, executable) |

```java
@Test
void test() {
  Study study = new Study(3);
  
  assertAll(
    () -> assertNotNull(study),
    () -> assertEquals(StudyStatus.DRAFT, study.getStatus(), "스터디를 처음 만들면 상태값이 DRAFT 여야 한다."),
    () -> assertTrue(study.getLimit() > 0, "스터티 최대 참석 가능 인원은 0보다 커야 한다")
  );  
}
```

## JUnit 5 조건에 따라 테스트 실행하기
특정 조건을 만족하는 경우 테스트 실행
- assumeTrue
- assumingThat
- @EnabledOnOs
- @DisabledOnOs
- @EnabledOnJre
- @EnabledForJreRange
- @EnabledIfEnvironmentVariable
- @EnabledIfSystemProperty

```java
@Test
@EnabledOnOs(OS.MAC)
void test1() {
  System.out.println("EnabledOnOs OS.MAC");
}

@Test
@DisabledOnOs(OS.WINDOWS)
void test2() {
  System.out.println("DisabledOnOs OS.WINDOWS");
}

@Test
@EnabledForJreRange(min = JRE.JAVA_8, max = JRE.JAVA_11)
// @EnabledOnJre(value = {JRE.JAVA_8})
// @EnabledIfEnvironmentVariable(named = "TEST", matches = "local")
// @EnabledIfSystemProperty(named = "TEST", matches = "local")
void test3() {
  System.out.println("EnabledForJreRange JAVA_8 ~ JAVA_11");
}
```

## Junit 5 테스트 반복하기
**@RepeatedTest**
- 반복 횟수와 반복 테스트 이름을 설정할 수 있다.
  - {displayName}
  - {currentRepetition}
  - {totalRepetitions}
- RepetitionInfo 타입의 인자를 받을 수 있다.

```java
@DisplayName("테스트 반복, RepeatedTest")
@RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetitions}")
void test(RepetitionInfo info) {
  System.out.println("repeat " + info.getCurrentRepetition() + "/" + info.getTotalRepetitions());
}
```

**@ParameterizedTest**  
테스트에 여러 다른 매개변수를 대입해가며 반복 실행한다

인자 값들의 소스
- @ValueSource : 해당 annotation 에 지정한 배열을 파라미터 값으로 순서대로 넘겨준다.
- @NullSource, @EmptySource, @NullAndEmptySource
- @EnumSource
- @MethodSource
- @CvsSource
- @CvsFileSource
- @ArgumentSource

```java
@ParameterizedTest
@ValueSource(ints = {1, 3, 5, -3, 15, Integer.MAX_VALUE})
void test1(int number) {
    assertTrue(Numbers.isOdd(number));
}

@ParameterizedTest
@NullAndEmptySource
void test2(String input) {
  assertTrue(Strings.isBlank(input));
}

@DisplayName("윤년이 아닌경우 4, 6, 8, 11 의 일수가 30일인지 확인")
@ParameterizedTest
@EnumSource(value = Month.class, names = {"APRIL", "JUNE", "SEPTEMBER", "NOVEMBER"})
void test3(Month month) {
  assertEquals(30, month.length(false));
}

@ParameterizedTest(name = "{index}: {3}")
@MethodSource("provideStringsForIsBlank")
void test4(String input, boolean expected, String message) {
  assertEquals(expected, Strings.isBlank(input), message);
}

private static Stream<Arguments> provideStringsForIsBlank() {
  return Stream.of(
    Arguments.of(null, true, "메세지1"),
    Arguments.of("", true, "메세지2"),
    Arguments.of("  ", true, "메세지3"),
    Arguments.of("not blank", false, "메세지4")
  );
}
```

## JUnit 5 테스트 인스턴스
기본 전략 : 테스트 메서드 마다 테스트 인스턴스를 새로 만든다.  
테스트 메소드를 독립적으로 실행하여 예상치 못한 부작용을 방지하기 위함

@TestInstance(Lifecycle.PER_CLASS)  
- 테스트 클래스당 인스턴스를 하나만 만들어 사용한다.  
- 경우에 따라, 테스트 간에 공유하는 모든 상태를 @BeforeEach 또는 @AfterEach 에서 초기화 할 필요가 있다.
- @BeforeAll 과 @AfterAll 을 인스턴스 메소드 또는 인터페이스에 정의한 default 메소드로 정의할 수도 있다.

```java
@TestInstance(Lifecycle.PER_CLASS)
class InstanceTest {

  int value;

  @Test
  void test1() {
    System.out.println("value = " + ++value);
    System.out.println(this);
  }

  @Test
  void test2() {
    System.out.println("value = " + ++value);
    System.out.println(this);
  }

  @BeforeAll
  void beforeAll() {
    System.out.println("beforeAll");
  }

  @AfterAll
  void afterAll() {
    System.out.println("afterAll");
  }
}
```

## JUnit 테스트 순서
- 메서드당 단위테스트가 독립적으로 실행이 가능해야함 서로간 의존성이 없어야 한다  
  하지만 통합, 시나리오 테스트 등 순서대로 실행을 해야할 경우 지정 가능
- @TestMethodOrder 가 선언되어있지 않다면 기본 알고리즘에 의해 순서가 정해지지만 아닌 경우도 있다
- @TestInstance(value = PER_CLASS) 와 같이 사용하여 상태를 공유하면 순서를   
  유지할 수도 있고 @TestMethodOrder 만 사용해도 된다

MethodOrderer 구현체
- Alphanumeric
- MethodName
- DisplayName
- Random

```java
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderTest {

  @Test
  @Order(1)
  void a() {
    System.out.println("a");
  }

  @Test
  @Order(2)
  void c() {
    System.out.println("c");
  }

  @Test
  @Order(3)
  void d() {
    System.out.println("d");
  }
}
```

***
# Mockito
Mock: 진짜 객체와 비슷하게 동작하지만 프로그래머가 직접 그 객체의 행동을 관리하는 객체.  
Mockito: Mock 객체를 쉽게 만들고 관리하고 검증할 수 있는 방법을 제공한다.

## Mock 객체 만들기
**@Mock** : mock 객체는 가짜 객체이며 그 안에 메소드 호출해서 사용하려면 반드시 스터빙(stubbing) 해야함

- 메소드로 만들기
    ```java
    void createMock() {
      MemberService memberService = mock(MemberService.class);
      StudyRepository studyRepository = mock(StudyRepository.class);
    }
    ```
- @Mock 어노테이션으로 만들기 : JUnit 5 extension으로 **MockitoExtension**을 사용해야 한다.
    1. 필드
        ```java
        @ExtendWith(MockitoExtension.class)
        class StudyServiceTest {
    
          @Mock 
          MemberService memberService;
       
          @Mock 
          StudyRepository studyRepository;
       }
        ```
    
  2. 메소드 매개변수
        ```java
        @ExtendWith(MockitoExtension.class)
        class StudyServiceTest {
        
            @Test
            void createStudyService(@Mock MemberService memberService,
                                    @Mock StudyRepository studyRepository) {
                StudyService studyService = new StudyService(memberService, studyRepository);
                assertNotNull(studyService);
            }
        }
        ```

**@Spy** : 객체는 진짜 객체이며 메소드 실행 시 스터빙을 하지 않으면 기존 객체의 로직을 실행한 값을, 스터빙을 한 경우엔 스터빙 값을 리턴
- 메소드로 만들기
    ```java
    List<String> spyList = spy(new ArrayList<String>());
    ```

- @Spy  어노테이션으로 만들기
    ```java
    @Spy
    ArrayList<String> spiedList;
    ```

**@InjectMocks** : @Mock이나 @Spy 객체를 자신의 멤버 클래스와 일치하면 주입
- @InjectMocks  어노테이션으로 만들기  
  *InjectMocks 의 대상의 일부분만 Mocking 하기위해선 @Spy와 같이 사용 해야함
    ```java
    //@Spy
    @InjectMocks
    StudyService studyService;

    @Mock
    MemberService memberService;

    @Spy
    StudyRepository studyRepository;
    ```

## Mock 객체 Stubbing
모든 Mock 객체의 행동
- Null을 리턴한다. (Optional 타입은 Optional.empty 리턴)
- Primitive 타입은 기본 Primitive 값.
- 콜렉션은 비어있는 콜렉션.
- Void 메소드는 예외를 던지지 않고 아무런 일도 발생하지 않는다.

```java
@ExtendWith(MockitoExtension.class)
class StubbingTest {

  @Mock
  MemberService memberService;

  @Mock
  StudyRepository studyRepository;
  
  @Test
  void stubbing() {
    when(memberService.findById(1L)).thenReturn(member);

    // ArgumentMatchers 사용
    when(memberService.findById(any())).thenReturn(member);

    // 메서드 호출시 예외 발생
    when(memberService.findById(any())).thenThrow(IllegalArgumentException.class);
    doThrow(IllegalStateException.class).when(memberService).validate(any());

    // 동일한 파라미터 여러번 호출시 다르게 처리
    when(memberService.findById(1L))
        .thenReturn(member)
        .thenThrow(IllegalArgumentException.class)
        .thenReturn(null);
  } 
}
```

## Mock 객체 확인
Mock 객체가 어떻게 사용이 됐는지 확인할 수 있다.
- 특정 메소드가 특정 매개변수로 몇번 호출 되었는지, 최소 한번은 호출 됐는지, 전혀 호출되지 않았는지
- 어떤 순서대로 호출했는지
- 특정 시간 이내에 호출됐는지
- 특정 시점 이후에 아무 일도 벌어지지 않았는지
```java
@ExtendWith(MockitoExtension.class)
class StubbingTest {

  @Mock
  MemberService memberService;

  @Mock
  StudyRepository studyRepository;
  
  @Test
  void stubbing() {
    verify(memberService, times(1)).notify(study);
    verify(memberService, times(1)).notify(member);
    verify(memberService, atLeast(1)).notify(study);
    verify(memberService, atLeastOnce()).notify(study);
    verify(memberService, atMost(1)).notify(study);
    verify(memberService, atMostOnce()).notify(study);
    verify(memberService, never()).validate(any());

    // 메소드 호출 순서 검증
    InOrder inOrder = inOrder(memberService);
    inOrder.verify(memberService).notify(study);
    inOrder.verify(memberService).notify(member);
    
    // verify 이후 다른 인터랙션이 없었다는 것을 검증
    verifyNoMoreInteractions(memberService);

    // 해당 메소드 가 5000ms 안에 실행 되었는지 확인 (JUnit assertTimeout 으로 검증하는것이 더 나아보임)
    verify(memberService, timeout(5000).atLeastOnce()).notify(study);
  } 
}
```

## BDD Mockito
**BDD**는 애플리케이션이 어떻게 “행동”해야 하는지에 대한 공통된 이해를 구성하는 방법으로, TDD에서 창안했다.  
행동에 대한 스팩
- Title
- Narrative   
  - As a  / I want / so that
- Acceptance criteria 
  - Given / When / Then

```java
@ExtendWith(MockitoExtension.class)
class StubbingTest {

  @Mock
  MemberService memberService;

  @Mock
  StudyRepository studyRepository;
  
  @Test
  void stubbing() {      
    // given
    given(studyRepository.save(study)).willReturn(study);
    given(studyRepository.save(any())).willReturn(study);
    given(studyRepository.save(study)).willThrow(IllegalStateException.class);

    // when
    // 메서드 호출

    // then
    InOrder inOrder = inOrder(memberService);
    then(memberService).should(times(1)).notify(study);
    then(memberService).should(atLeastOnce()).notify(study);
    then(memberService).should(inOrder).notify(study);
  } 
}
```

## [MockedStatic mockito-inline](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#48)
Mockito를 이용하여서도 static method를 테스트
```java
@DisplayName("static mock test")
@Slf4j
class RandomUtilsTest {

  @DisplayName("try with resources block (Mock scope) 활용")
  @Test
  void staticMockito() {

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
```

## Spring boot +  Mockito
- **@MockBean** : ApplicationContext에 mock객체를 추가  
- **@SpyBean** : ApplicationContext에 spy객체를 추가
```java
@SpringBootTest
@AutoConfigureMockMvc
class StudyControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  private MemberService memberService;

  @SpyBean
  private StudyService studyService;

  @Test
  void sampleStudy() throws Exception {
    // given
    int limit = 999999;

    Study study = new Study(limit, "sample-study");
    given(studyService.createSampleStudy()).willReturn(study);

    // when
    ResultActions result = mockMvc.perform(get("/study"));

    // then
    result.andDo(print())
        .andExpect(jsonPath("$.limitCount").value(limit));
  }
}
```

## 참조
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)