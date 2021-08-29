package com.ask.thejavatest.web;

import com.ask.thejavatest.domain.Study;
import com.ask.thejavatest.study.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StudyController {

  private final StudyService studyService;

  @GetMapping("/study")
  public Study study() {
    return studyService.createSampleStudy();
  }
}
