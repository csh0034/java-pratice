package com.ask.thejavatest.member;

import com.ask.thejavatest.domain.Member;
import com.ask.thejavatest.domain.Study;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

  @Override
  public void validate(Long memberId) throws InvalidMemberException {
  }

  @Override
  public Member findById(Long memberId) throws MemberNotFoundException {
    return null;
  }

  @Override
  public void notify(Study newStudy) {
  }

  @Override
  public void notify(Member member) {

  }
}
