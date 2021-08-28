package com.ask.thejavatest.member;

import com.ask.thejavatest.domain.Member;
import com.ask.thejavatest.domain.Study;

public interface MemberService {
  void validate(Long memberId) throws InvalidMemberException;

  Member findById(Long memberId) throws MemberNotFoundException;

  void notify(Study newStudy);

  void notify(Member member);
}
