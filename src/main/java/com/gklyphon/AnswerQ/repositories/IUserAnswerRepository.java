package com.gklyphon.AnswerQ.repositories;

import com.gklyphon.AnswerQ.models.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserAnswerRepository extends JpaRepository<Long, UserAnswer> {
    Page<UserAnswer> findAllByForm(Form form, Pageable pageable);
    Page<UserAnswer> findAllByUser(User user, Pageable pageable);
    Page<UserAnswer> findAllByQuestion(Question question, Pageable pageable);
    Page<Answer> findAllByAnswer(Answer answer, Pageable pageable);
}
