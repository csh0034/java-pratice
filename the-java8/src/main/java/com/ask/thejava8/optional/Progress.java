package com.ask.thejava8.optional;

import java.time.Duration;

public class Progress {

  private Duration studyDuration;
  private boolean finished;

  public Duration getStudyDuration() {
    return studyDuration;
  }

  public boolean isFinished() {
    return finished;
  }

  @Override
  public String toString() {
    return "Progress{" +
        "studyDuration=" + studyDuration +
        ", finished=" + finished +
        '}';
  }
}
