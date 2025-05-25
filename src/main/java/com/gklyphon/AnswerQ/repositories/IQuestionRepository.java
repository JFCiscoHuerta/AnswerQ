package com.gklyphon.AnswerQ.repositories;

import com.gklyphon.AnswerQ.models.Form;
import com.gklyphon.AnswerQ.models.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IQuestionRepository extends JpaRepository<Long, Question> {
    Page<Question> findAllByForm(Form form, Pageable pageable);
}
