package com.ask.thejavatest.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.ask.thejavatest.domain.Study;
import com.ask.thejavatest.member.MemberService;
import com.ask.thejavatest.study.StudyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

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