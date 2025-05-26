package com.gklyphon.AnswerQ.services;

import com.gklyphon.AnswerQ.models.Answer;
import com.gklyphon.AnswerQ.models.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAnswerService extends IService<Answer, Answer> {
    Page<Answer> findAllByQuestion(Question question, Pageable pageable);
}
