package com.ask.thejavatest;

import com.ask.thejavatest.domain.StudyStatus;
import java.util.concurrent.TimeUnit;

public class Study {

  public static String EXCEPTION_MESSAGE = "limit must be gt 0";

  private StudyStatus status = StudyStatus.DRAFT;

  private int limit;

  public Study() {
  }

  public Study(StudyStatus status) {
    this.status = status;
    try {
      TimeUnit.MILLISECONDS.sleep(400);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public Study(int limit) {
    if (limit < 0) {
      throw new IllegalArgumentException(EXCEPTION_MESSAGE);
    }
    this.limit = limit;
  }

  public StudyStatus getStatus() {
    return this.status;
  }

  public int getLimit() {
    return limit;
  }
}
