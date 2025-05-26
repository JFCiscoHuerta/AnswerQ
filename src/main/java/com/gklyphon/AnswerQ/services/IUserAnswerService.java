package com.gklyphon.AnswerQ.services;

import com.gklyphon.AnswerQ.models.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserAnswerService extends IService<UserAnswer, UserAnswer> {
    Page<UserAnswer> findAllByForm(Form form, Pageable pageable);
    Page<UserAnswer> findAllByUser(User user, Pageable pageable);
    Page<UserAnswer> findAllByQuestion(Question question, Pageable pageable);
    Page<Answer> findAllByAnswer(Answer answer, Pageable pageable);
}
