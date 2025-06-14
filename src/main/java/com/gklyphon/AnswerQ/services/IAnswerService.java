package com.gklyphon.AnswerQ.services;

import com.gklyphon.AnswerQ.models.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAnswerService extends IService<Answer, Answer> {
    Page<Answer> findAllByQuestion_Id(Long questionId, Pageable pageable);
}
