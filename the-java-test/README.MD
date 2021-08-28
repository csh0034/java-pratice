# Junit5
...

# Mockito
Mock: 진짜 객체와 비슷하게 동작하지만 프로그래머가 직접 그 객체의 행동을 관리하는 객체.  
Mockito: Mock 객체를 쉽게 만들고 관리하고 검증할 수 있는 방법을 제공한다.

### **Mock 객체 만들기**

- 메소드로 만들기
    ```java
    void createMock() {
      MemberService memberService = mock(MemberService.class);
      StudyRepository studyRepository = mock(StudyRepository.class);
    }
    ```
- @Mock 애노테이션으로 만드는 방법 : JUnit 5 extension으로 **MockitoExtension**을 사용해야 한다.
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

### **Mock 객체 Stubbing**
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

### **Mock 객체 확인**
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