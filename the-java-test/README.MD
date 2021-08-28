# Junit5
...

# Mockito
Mock: 진짜 객체와 비슷하게 동작하지만 프로그래머가 직접 그 객체의 행동을 관리하는 객체.  
Mockito: Mock 객체를 쉽게 만들고 관리하고 검증할 수 있는 방법을 제공한다.

## **Mock 객체 만들기**

- 메소드로 만들기
    ```java
    MemberService memberService = mock(MemberService.class);
    StudyRepository studyRepository = mock(StudyRepository.class);
    ```
- @Mock 애노테이션으로 만드는 방법 : JUnit 5 extension으로 **MockitoExtension**을 사용해야 한다.
    1. 필드
        ```java
        @ExtendWith(MockitoExtension.class)
        class StudyServiceTest {
    
        @Mock MemberService memberService;
    
        @Mock StudyRepository studyRepository;
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
