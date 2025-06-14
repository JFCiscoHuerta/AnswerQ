package com.gklyphon.AnswerQ.services;

import com.gklyphon.AnswerQ.models.Form;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IFormService extends IService<Form, Form> {
    Page<Form> findAllByUser_Id(Long userId, Pageable pageable);
}
