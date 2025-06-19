package com.gklyphon.AnswerQ.services.impl;

import com.gklyphon.AnswerQ.exceptions.exception.ElementNotFoundException;
import com.gklyphon.AnswerQ.models.Form;
import com.gklyphon.AnswerQ.repositories.IFormRepository;
import com.gklyphon.AnswerQ.services.IFormService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

/**
 * Service implementation for managing {@link Form} entities.
 * Implements {@link IFormService} interface.
 *
 * @author JFCiscoHuerta
 * @since 2025-06-16
 */
@Service
public class FormServiceImpl implements IFormService {

    private final IFormRepository formRepository;

    public FormServiceImpl(IFormRepository formRepository) {
        this.formRepository = formRepository;
    }

    /**
     * Finds all forms created by a user, with pagination.
     *
     * @param userId The ID of the user.
     * @param pageable Pagination parameters.
     * @return A page of forms created by the user.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Form> findAllByUser_Id(Long userId, Pageable pageable) {
        return formRepository.findAllByUser_Id(userId, pageable);
    }

    /**
     * Finds a form by its ID.
     *
     * @param id The ID of the form.
     * @return The found form.
     * @throws NoSuchElementException if no form is found.
     */
    @Override
    @Transactional(readOnly = true)
    public Form findById(Long id) throws ElementNotFoundException {
        return formRepository.findById(id).orElseThrow(() -> new ElementNotFoundException("Form not found."));
    }

    /**
     * Finds all forms with pagination.
     *
     * @param pageable Pagination parameters.
     * @return A page of all forms.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Form> findAll(Pageable pageable) {
        return formRepository.findAll(pageable);
    }

    /**
     * Saves a new form.
     *
     * @param form The form to save.
     * @return The saved form.
     * @throws Exception if any error occurs during saving.
     */
    @Override
    @Transactional
    public Form save(Form form) throws Exception {
        try {
            return formRepository.save(form);
        } catch (ServiceException ex) {
            throw new ServiceException("Unexpected Service Error While Saving", ex);
        } catch (Exception ex) {
            throw new Exception("Unexpected Error While Saving", ex);
        }
    }

    /**
     * Updates an existing form by its ID.
     *
     * @param id The ID of the form to update.
     * @param form The new data for the form.
     * @return The updated form.
     * @throws Exception if any error occurs during update.
     */
    @Override
    @Transactional
    public Form update(Long id, Form form) throws Exception {
        Form originalForm = findById(id);
        try {
            BeanUtils.copyProperties(form, originalForm, "id");
            return formRepository.save(originalForm);
        } catch (ServiceException ex) {
            throw new ServiceException("Unexpected Service Error While Updating", ex);
        } catch (Exception ex) {
            throw new Exception("Unexpected Error While Updating", ex);
        }
    }

    /**
     * Deletes a form by its ID.
     *
     * @param id The ID of the form to delete.
     * @throws Exception if any error occurs during deletion.
     */
    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        findById(id);
        try {
            formRepository.deleteById(id);
        } catch (ServiceException ex) {
            throw new ServiceException("Unexpected Service Error While Deleting", ex);
        } catch (Exception ex) {
            throw new Exception("Unexpected Error While Deleting", ex);
        }
    }
}
