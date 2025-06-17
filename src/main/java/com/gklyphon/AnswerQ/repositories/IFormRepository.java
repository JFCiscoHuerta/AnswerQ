package com.gklyphon.AnswerQ.repositories;

import com.gklyphon.AnswerQ.models.Form;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link Form} entities in the database.
 *
 * @author JFCiscoHuerta
 * @since 2025-06-16
 */
public interface IFormRepository extends JpaRepository<Form, Long> {

    /**
     * Finds a page of forms created by a specific user.
     *
     * @param userId The ID of the user.
     * @param pageable Pageable object to define page size and number.
     * @return A page of forms created by the specified user.
     */
    Page<Form> findAllByUser_Id(Long userId, Pageable pageable);
}
