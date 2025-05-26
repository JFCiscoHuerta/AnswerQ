package com.gklyphon.AnswerQ.repositories;

import com.gklyphon.AnswerQ.models.Form;
import com.gklyphon.AnswerQ.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFormRepository extends JpaRepository<Form, Long> {
    Page<Form> findAllByUser(User user, Pageable pageable);
}
