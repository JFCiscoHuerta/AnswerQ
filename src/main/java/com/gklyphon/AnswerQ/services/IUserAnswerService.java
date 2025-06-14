package com.gklyphon.AnswerQ.services;

import com.gklyphon.AnswerQ.models.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserAnswerService extends IService<UserAnswer, UserAnswer> {
    Page<UserAnswer> findAllByForm(Long formId, Pageable pageable);
    Page<UserAnswer> findAllByUser(Long userId, Pageable pageable);
    Page<UserAnswer> findAllByQuestion(Long questionId, Pageable pageable);
    Page<UserAnswer> findAllByAnswer(Long answerId, Pageable pageable);
}
