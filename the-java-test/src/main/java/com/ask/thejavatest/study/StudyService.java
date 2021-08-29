package com.ask.thejavatest.study;

import com.ask.thejavatest.domain.Member;
import com.ask.thejavatest.domain.Study;
import com.ask.thejavatest.member.MemberService;
import org.springframework.stereotype.Service;

@Service
public class StudyService {

  private final MemberService memberService;

  private final StudyRepository repository;

  public StudyService(MemberService memberService, StudyRepository repository) {
    assert memberService != null;
    assert repository != null;
    this.memberService = memberService;
    this.repository = repository;
  }

  public Study createNewStudy(Long memberId, Study study) {
    Member member = memberService.findById(memberId);
    if (member == null) {
      throw new IllegalArgumentException("Member doesn't exist for id: '" + memberId + "'");
    }
    study.setOwner(member);
    Study newStudy = repository.save(study);

    memberService.notify(newStudy);
    memberService.notify(member);

    return newStudy;
  }

  public Study openStudy(Study study) {
    study.open();
    Study openedStudy = repository.save(study);
    memberService.notify(openedStudy);
    return openedStudy;
  }

  public Study createSampleStudy() {
    Study study = new Study(100, "sample-study");
    study.setId(1L);
    return study;
  }
}
