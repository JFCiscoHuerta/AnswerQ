package com.gklyphon.AnswerQ.services;

import com.gklyphon.AnswerQ.models.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing {@link UserAnswer} entities.
 * Extends the generic {@link IService} for basic operations.
 *
 * @author JFCiscoHuerta
 * @since 2025-06-16
 */
public interface IUserAnswerService extends IService<UserAnswer, UserAnswer> {

    /**
     * Finds a page of user answers by form ID.
     *
     * @param formId The ID of the form.
     * @param pageable Pagination information.
     * @return A page of user answers for the form.
     */
    Page<UserAnswer> findAllByForm(Long formId, Pageable pageable);

    /**
     * Finds a page of user answers by user ID.
     *
     * @param userId The ID of the user.
     * @param pageable Pagination information.
     * @return A page of user answers by the user.
     */
    Page<UserAnswer> findAllByUser(Long userId, Pageable pageable);

    /**
     * Finds a page of user answers by question ID.
     *
     * @param questionId The ID of the question.
     * @param pageable Pagination information.
     * @return A page of user answers for the question.
     */
    Page<UserAnswer> findAllByQuestion(Long questionId, Pageable pageable);

    /**
     * Finds a page of user answers by answer ID.
     *
     * @param answerId The ID of the answer.
     * @param pageable Pagination information.
     * @return A page of user answers for the answer.
     */
    Page<UserAnswer> findAllByAnswer(Long answerId, Pageable pageable);
}
