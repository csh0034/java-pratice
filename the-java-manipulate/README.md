# Java 코드를 조작하는 다양한방법

## JVM 이해하기

### 1. 자바, JVM, JDK, JRE

![01.png](./images/01.png)

JVM (Java Virtual Machine)  
- 자바 가상 머신으로 자바 바이트 코드(.class 파일)를 OS에 특화된 코드로 변환(인터프리터와 JIT 컴파일러)하여 실행한다.
- 바이트 코드를 실행하는 표준(JVM 자체는 표준)이자 구현체(특정 밴더가 구현한 JVM)다.
- JVM 밴더: 오라클, 아마존, Azul, ...
- 특정 플랫폼에 종속적.

JRE (Java Runtime Environment): JVM + 라이브러리
- 자바 애플리케이션을 실행할 수 있도록 구성된 배포판.
- JVM과 핵심 라이브러리 및 자바 런타임 환경에서 사용하는 프로퍼티 세팅이나 리소스 파일을 가지고 있다.
- 개발 관련 도구는 포함하지 않는다. (그건 JDK에서 제공)

JDK (Java Development Kit): JRE + 개발 툴
- JRE + 개발에 필요할 툴
- 소스 코드를 작성할 때 사용하는 자바 언어는 플랫폼에 독립적.
- 오라클은 자바 11부터는 JDK만 제공하며 JRE를 따로 제공하지 않는다.
- Write Once Run Anywhere

자바
- 프로그래밍 언어
- JDK에 들어있는 자바 컴파일러(javac)를 사용하여 바이트코드(.class 파일)로 컴파일 할 수 있다.
- 자바 유료화? 오라클에서 만든 Oracle JDK 11 버전부터 상용으로 사용할 때 유료.

JVM 언어
- JVM 기반으로 동작하는 프로그래밍 언어
- 클로저, 그루비, JRuby, Jython, Kotlin, Scala, ...

***
###  2. JVM 구조

![02.png](./images/02.png)

클래스 로더 시스템
- .class 에서 바이트코드를 읽고 메모리에 저장
- 로딩 : 클래스 읽어오는 과정
- 링크 : 레퍼런스를 연결하는 과정
- 초기화 : static 값들 초기화 및 변수에 할당

메모리
- 메소드 영역 : 클래스 멤버 변수, 메소드 정보, Type(Class or interface)정보, Constant Pool, static, final 변수 등이 생성된다.  
  상수 풀은 모든 Symbolic Refernce를 포함, 공유 자원이다.
- 힙 영역 : 동적으로 생성된 오브젝트와 배열이 저장되는 곳으로 Garbage Collection의 대상이 되는 영역, 공유 자원이다.
- 스택 영역 :  쓰레드 마다 런타임 스택을 만들고, 그 안에 메소드 호출을 스택 프레임이라 부르는 블럭으로 쌓는다.  
  호출된 메서드의 매개변수, 지역변수, 리턴 값 및 연산 시 일어나는 값들을 임시로 저장, 쓰레드 종료하면 런타임 스택도 사라진다.
- PC(Program Counter) 레지스터 : 쓰레드 마다 쓰레드 내 현재 실행할 instruction의 위치를 가리키는 포인터가 생성된다.
- 네이티브 메소드 스택 : 자바외 언어로 작성된 네이티브 코드를 위한 메모리 영역

실행 엔진
- 인터프리터: 바이트 코드를 한줄 씩 실행.
- JIT 컴파일러: 인터프리터 효율을 높이기 위해, 인터프리터가 반복되는 코드를 발견하면 JIT 컴파일러로 반복되는 코드를 모두  
  네이티브 코드로 바꿔둔다. 그 다음부터 인터프리터는 네이티브 코드로 컴파일된 코드를 바로 사용한다.
- GC(Garbage Collector): 더이상 참조되지 않는 객체를 모아서 정리한다.
- JNI(Java Native Interface)
- 자바 애플리케이션에서 C, C++, 어셈블리로 작성된 함수를 사용할 수 있는 방법 제공
- Native 키워드를 사용한 메소드 호출

네이티브 메소드 라이브러리
- C, C++로 작성 된 라이브러리

***
### 3. 클래스 로더

![03.png](./images/03.png)

클래스 로더
- 로딩, 링크, 초기화 순으로 진행된다.
- 로딩
  - 클래스 로더가 .class 파일을 읽고 그 내용에 따라 적절한 바이너리 데이터를 만들고 “메소드” 영역에 저장.
  - 이때 메소드 영역에 저장하는 데이터
    - FQCN
    - 클래스 | 인터페이스 | enum
    - 메소드와 변수
  - 로딩이 끝나면 해당 클래스 타입의 Class 객체를 생성하여 “힙" 영역에 저장.
- 링크
  - Verify, Prepare, Reolve(optional) 세 단계로 나눠져 있다.
  - 검증: .class 파일 형식이 유효한지 체크한다.
  - Preparation: 클래스 변수(static 변수)와 기본값에 필요한 메모리
  - Resolve: 심볼릭 메모리 레퍼런스를 메소드 영역에 있는 실제 레퍼런스로 교체한다.
- 초기화
  - Static 변수의 값을 할당한다. (static 블럭이 있다면 이때 실행된다.)
- 클래스 로더는 계층 구조로 이뤄져 있으면 기본적으로 세가지 클래스 로더가 제공된다.
  - 부트 스트랩 클래스 로더 : JAVA_HOME\lib에 있는 코어 자바 API를 제공한다. 최상위 우선순위를 가진 클래스 로더
  - 플랫폼 클래스로더 : JAVA_HOME\lib\ext 폴더 또는 java.ext.dirs 시스템 변수에 해당하는 위치에 있는 클래스를 읽는다.
  - 애플리케이션 클래스로더 : 애플리케이션 클래스패스(애플리케이션 실행할 때 주는 -classpath 옵션 또는 java.class.path  
    환경 변수의 값에 해당하는 위치)에서 클래스를 읽는다. JAR 파일 안에 있는 Manifest 파일의 Class-Path 속성값으로 지정된   
    폴더에 있는 클래스를 로딩한다.

***  
## 바이트 코드 조작

**Jacoco plugin** 을 사용하여 코드 커버리지 측정

``` shell
$ mvn clean verify
```
pom.xml
```xml
<build>
  <plugins>
    <!-- mvn test 실행하기 위해선 추가해야함 -->
    <plugin>
      <artifactId>maven-surefire-plugin</artifactId>
      <version>2.22.0</version>
    </plugin>
    <plugin>
      <groupId>org.jacoco</groupId>
      <artifactId>jacoco-maven-plugin</artifactId>
      <version>0.8.4</version>
      <executions>
        <execution>
          <goals>
            <goal>prepare-agent</goal>
          </goals>
        </execution>
        <execution>
          <id>report</id>
          <phase>prepare-package</phase>
          <goals>
            <goal>report</goal>
          </goals>
        </execution>
        <execution>
          <id>jacoco-check</id>
          <goals>
            <goal>check</goal>
          </goals>
          <configuration>
            <rules>
              <rule>
                <element>PACKAGE</element>
                <limits>
                  <limit>
                    <counter>LINE</counter>
                    <value>COVEREDRATIO</value>
                    <minimum>0.30</minimum><!-- 코드 커버리지 통과 비율을 설정 -->
                  </limit>
                </limits>
              </rule>
            </rules>
          </configuration>
        </execution>
      </executions>
    </plugin>
  </plugins>
</build>
```

