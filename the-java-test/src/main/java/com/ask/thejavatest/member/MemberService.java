package com.ask.thejavatest.member;

import com.ask.thejavatest.domain.Member;

public interface MemberService {
  void validate(Long memberId) throws InvalidMemberException;

  Member findById(Long memberId) throws MemberNotFoundException;
}
