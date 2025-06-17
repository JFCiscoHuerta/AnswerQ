package com.gklyphon.AnswerQ.services;

import com.gklyphon.AnswerQ.models.Form;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing {@link Form} entities.
 * Extends the generic {@link IService} for basic CRUD operations.
 *
 * @author JFCiscoHuerta
 * @since 2025-06-16
 */
public interface IFormService extends IService<Form, Form> {

    /**
     * Finds a page of forms belonging to a specific user.
     *
     * @param userId The ID of the user.
     * @param pageable Pagination information.
     * @return A page of forms owned by the user.
     */
    Page<Form> findAllByUser_Id(Long userId, Pageable pageable);
}
