package com.gklyphon.AnswerQ.repositories;

import com.gklyphon.AnswerQ.models.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for accessing {@link Answer} data from the database.
 *
 * @author JFCiscoHuerta
 * @since 2025-06-16
 */
public interface IAnswerRepository extends JpaRepository<Answer, Long> {

    /**
     * Finds a page of answers that belong to a specific question.
     *
     * @param questionId The ID of the question.
     * @param pageable Pageable object to control pagination.
     * @return A page of answers for the given question.
     */
    Page<Answer> findAllByQuestion_Id(Long questionId, Pageable pageable);
}
