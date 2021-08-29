package com.ask.thejavatest.study;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.ask.thejavatest.domain.Member;
import com.ask.thejavatest.domain.Study;
import com.ask.thejavatest.domain.StudyStatus;
import com.ask.thejavatest.member.MemberService;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

  @Mock
  MemberService memberService;

  @Mock
  StudyRepository studyRepository;

  @DisplayName("메소드로 작성하여 mock 객체 만들기")
  @Test
  void createStudyService1() {
    MemberService memberService = mock(MemberService.class);
    StudyRepository studyRepository = mock(StudyRepository.class);
    StudyService studyService = new StudyService(memberService, studyRepository);
    assertNotNull(studyService);
  }

  @DisplayName("필드 어노테이션 기반 mock 객체 만들기")
  @Test
  void createStudyService2() {
    StudyService studyService = new StudyService(memberService, studyRepository);
    assertNotNull(studyService);
  }

  @DisplayName("메소드 매개변수 어노테이션 기반 mock 객체 만들기")
  @Test
  void createStudyService2(@Mock MemberService memberService, @Mock StudyRepository studyRepository) {
    StudyService studyService = new StudyService(memberService, studyRepository);
    assertNotNull(studyService);
  }

  @DisplayName("Mock 객체 Stubbing")
  @Nested
  class Stubbing {

    @DisplayName("객체는 null 리턴")
    @Test
    void stubbing1() {
      Member member = memberService.findById(1L);
      assertNull(member);
    }

    @DisplayName("Optional 은 empty 리턴")
    @Test
    void stubbing2() {
      Optional<Study> study = studyRepository.findById(1L);
      assertEquals(Optional.empty(), study);
    }

    @DisplayName("void 는 아무일도 안함")
    @Test
    void stubbing3() {
      memberService.validate(1L);
    }

    @DisplayName("when thenReturn 사용")
    @Test
    void stubbing4() {
      Member member = new Member();
      member.setId(1L);
      member.setEmail("test@naver.com");

      when(memberService.findById(1L)).thenReturn(member);

      Member findMember = memberService.findById(1L);
      System.out.println("findMember = " + findMember);

      assertNotNull(findMember);
      assertEquals(member, findMember);
    }

    @DisplayName("when thenReturn ArgumentMatchers any 사용")
    @Test
    void stubbing5() {
      Member member = new Member();
      member.setId(1L);
      member.setEmail("test@naver.com");

      when(memberService.findById(any())).thenReturn(member);

      assertEquals(member, memberService.findById(1L));
      assertEquals(member, memberService.findById(2L));
      assertEquals(member, memberService.findById(3L));
    }

    @DisplayName("when thenThrow, doThrow 예외 발생")
    @Test
    void stubbing6() {
      when(memberService.findById(any())).thenThrow(IllegalArgumentException.class);
      doThrow(IllegalStateException.class).when(memberService).validate(any());

      assertAll(
          () -> assertThrows(IllegalArgumentException.class, () -> memberService.findById(1L)),
          () -> assertThrows(IllegalStateException.class, () -> memberService.validate(1L))
      );
    }

    @DisplayName("동일한 매개변수로 여러번 호출될 때 다르게 처리")
    @Test
    void stubbing7() {
      long id = 1L;

      Member member = new Member();
      member.setId(id);
      member.setEmail("test@naver.com");

      when(memberService.findById(id))
          .thenReturn(member)
          .thenThrow(IllegalArgumentException.class)
          .thenReturn(null);

      assertAll(
          () -> assertEquals(member, memberService.findById(id)),
          () -> assertThrows(IllegalArgumentException.class, () -> memberService.findById(id)),
          () -> assertNull(memberService.findById(id))
      );
    }

    @DisplayName("createNewStudy stubbing 연습")
    @Test
    void stubbing8() {
      StudyService studyService = new StudyService(memberService, studyRepository);
      assertNotNull(studyService);

      Member member = new Member();
      member.setId(1L);
      member.setEmail("test@naver.com");

      Study study = new Study(10, "테스트");

      when(memberService.findById(1L)).thenReturn(member);
      when(studyRepository.save(study)).thenReturn(study);

      studyService.createNewStudy(1L, study);

      assertNotNull(study.getOwner());
      assertEquals(member, study.getOwner());
    }

    @DisplayName("verify, verifyNoMoreInteractions, inOrder, timeout 검증")
    @Test
    void stubbing9() {
      StudyService studyService = new StudyService(memberService, studyRepository);
      assertNotNull(studyService);

      Member member = new Member();
      member.setId(1L);
      member.setEmail("test@naver.com");

      Study study = new Study(10, "테스트");

      when(memberService.findById(1L)).thenReturn(member);
      when(studyRepository.save(study)).thenReturn(study);

      studyService.createNewStudy(1L, study);

      assertEquals(member, study.getOwner());

      verify(memberService, times(1)).notify(study);
      verify(memberService, times(1)).notify(member);
      verify(memberService, atLeast(1)).notify(study);
      verify(memberService, atLeastOnce()).notify(study);
      verify(memberService, atMost(1)).notify(study);
      verify(memberService, atMostOnce()).notify(study);
      verify(memberService, never()).validate(any());

      InOrder inOrder = inOrder(memberService);
      inOrder.verify(memberService).notify(study);
      inOrder.verify(memberService).notify(member);

      verifyNoMoreInteractions(memberService);

      verify(memberService, timeout(5000).atLeastOnce()).notify(study);
    }

    @DisplayName("Mockito Bdd Style")
    @Test
    void bdd() {
      // given
      StudyService studyService = new StudyService(memberService, studyRepository);
      assertNotNull(studyService);

      Member member = new Member();
      member.setId(1L);
      member.setEmail("test@naver.com");

      Study study = new Study(10, "테스트");

      given(memberService.findById(1L)).willReturn(member);
      given(studyRepository.save(study)).willReturn(study);

      // when
      studyService.createNewStudy(1L, study);

      // then
      then(memberService).should(times(1)).notify(study);
      then(memberService).should(atLeastOnce()).notify(member);
      then(memberService).should(never()).validate(any());

      then(memberService).shouldHaveNoMoreInteractions();
    }

    @DisplayName("Mockito 연습")
    @Test
    void bdd2() {
      // given
      StudyService studyService = new StudyService(memberService, studyRepository);
      Study study = new Study(10, "더 자바, 테스트");

      given(studyRepository.save(study)).willReturn(study);
      InOrder inOrder = inOrder(memberService);

      // when
      studyService.openStudy(study);

      // then
      assertEquals(StudyStatus.OPENED, study.getStatus());
      assertNotNull(study.getOpenedDateTime());

      then(memberService).should(atLeastOnce()).notify(study);
      then(memberService).should(inOrder).notify(study);
    }
  }
}