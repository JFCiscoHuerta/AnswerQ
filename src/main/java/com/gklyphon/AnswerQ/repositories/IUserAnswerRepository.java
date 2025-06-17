package com.gklyphon.AnswerQ.repositories;

import com.gklyphon.AnswerQ.models.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link UserAnswer} entities.
 *
 * @author JFCiscoHuerta
 * @since 2025-06-16
 */
public interface IUserAnswerRepository extends JpaRepository<UserAnswer, Long> {

    /**
     * Finds user answers for a specific form with pagination.
     *
     * @param formId The ID of the form.
     * @param pageable Pagination information.
     * @return A page of user answers linked to the form.
     */
    Page<UserAnswer> findAllByForm_Id(Long formId, Pageable pageable);

    /**
     * Finds user answers submitted by a specific user with pagination.
     *
     * @param userId The ID of the user.
     * @param pageable Pagination information.
     * @return A page of user answers given by the user.
     */
    Page<UserAnswer> findAllByUser_Id(Long userId, Pageable pageable);

    /**
     * Finds user answers for a specific question with pagination.
     *
     * @param questionId The ID of the question.
     * @param pageable Pagination information.
     * @return A page of user answers related to the question.
     */
    Page<UserAnswer> findAllByQuestion_Id(Long questionId, Pageable pageable);

    /**
     * Finds user answers that selected a specific answer with pagination.
     *
     * @param answerId The ID of the answer.
     * @param pageable Pagination information.
     * @return A page of user answers that chose the answer.
     */
    Page<UserAnswer> findAllByAnswer_Id(Long answerId, Pageable pageable);
}
