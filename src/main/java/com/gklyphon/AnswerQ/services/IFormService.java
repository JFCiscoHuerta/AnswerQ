package com.gklyphon.AnswerQ.services;

import com.gklyphon.AnswerQ.models.Form;
import com.gklyphon.AnswerQ.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IFormService extends IService<Form, Form> {
    Page<Form> findAllByUser(User user, Pageable pageable);
}
