package com.gklyphon.AnswerQ.services;

import com.gklyphon.AnswerQ.models.Form;
import com.gklyphon.AnswerQ.models.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IQuestionService extends IService<Question, Question> {
    Page<Question> findAllByForm(Form form, Pageable pageable);
}
