package com.gklyphon.AnswerQ.services;

import com.gklyphon.AnswerQ.models.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing {@link Answer} entities.
 * Extends the generic {@link IService} for basic operations.
 *
 * @author JFCiscoHuerta
 * @since 2025-06-16
 */
public interface IAnswerService extends IService<Answer, Answer> {

    /**
     * Finds a page of answers related to a specific question.
     *
     * @param questionId The ID of the question.
     * @param pageable Pagination information.
     * @return A page of answers for the question.
     */
    Page<Answer> findAllByQuestion_Id(Long questionId, Pageable pageable);
}
