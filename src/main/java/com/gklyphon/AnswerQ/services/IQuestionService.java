package com.gklyphon.AnswerQ.services;

import com.gklyphon.AnswerQ.models.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing {@link Question} entities.
 * Extends the generic {@link IService} for basic operations.
 *
 * @author JFCiscoHuerta
 * @since 2025-06-16
 */
public interface IQuestionService extends IService<Question, Question> {

    /**
     * Finds a page of questions for a given form.
     *
     * @param id The ID of the form.
     * @param pageable Pagination details.
     * @return A page of questions for the form.
     */
    Page<Question> findAllByForm_Id(Long id, Pageable pageable);
}
