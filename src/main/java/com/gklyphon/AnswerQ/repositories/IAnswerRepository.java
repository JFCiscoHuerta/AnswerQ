package com.gklyphon.AnswerQ.repositories;

import com.gklyphon.AnswerQ.models.Answer;
import com.gklyphon.AnswerQ.models.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAnswerRepository extends JpaRepository<Answer, Long> {
    Page<Answer> findAllByQuestion(Question question, Pageable pageable);
}
