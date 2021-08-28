package com.ask.thejavatest.study;

import static org.junit.jupiter.api.Assertions.*;

import com.ask.thejavatest.domain.Study;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class StudyRepositoryTest {

  @Autowired
  StudyRepository repository;

  @Test
  void save() {
    Study study = new Study(10, "Java");
    repository.save(study);
    List<Study> all = repository.findAll();
    assertEquals(1, all.size());
  }

}