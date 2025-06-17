package com.gklyphon.AnswerQ.repositories;

import com.gklyphon.AnswerQ.models.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for accessing {@link Question} data from the database.
 *
 * @author JFCiscoHuerta
 * @since 2025-06-16
 */
public interface IQuestionRepository extends JpaRepository<Question, Long> {

    /**
     * Finds a page of questions that belong to a specific form.
     *
     * @param id The ID of the form.
     * @param pageable Pageable object to control pagination.
     * @return A page of questions for the given form.
     */
    Page<Question> findAllByForm_Id(Long id, Pageable pageable);
}
