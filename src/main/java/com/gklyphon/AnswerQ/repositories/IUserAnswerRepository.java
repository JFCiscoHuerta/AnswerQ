package com.gklyphon.AnswerQ.repositories;

import com.gklyphon.AnswerQ.models.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserAnswerRepository extends JpaRepository<UserAnswer, Long> {
    Page<UserAnswer> findAllByForm_Id(Long formId, Pageable pageable);
    Page<UserAnswer> findAllByUser_Id(Long userId, Pageable pageable);
    Page<UserAnswer> findAllByQuestion_Id(Long questionId, Pageable pageable);
    Page<Answer> findAllByAnswer_Id(Long answerId, Pageable pageable);
}
