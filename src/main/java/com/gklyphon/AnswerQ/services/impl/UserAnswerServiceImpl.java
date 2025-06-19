package com.gklyphon.AnswerQ.services.impl;

import com.gklyphon.AnswerQ.exceptions.exception.ElementNotFoundException;
import com.gklyphon.AnswerQ.models.*;
import com.gklyphon.AnswerQ.repositories.IUserAnswerRepository;
import com.gklyphon.AnswerQ.services.IUserAnswerService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

/**
 * Service implementation for managing {@link UserAnswer} entities.
 * Implements {@link IUserAnswerService} interface.
 *
 * @author JFCiscoHuerta
 * @since  2025-06-16
 */
@Service
public class UserAnswerServiceImpl implements IUserAnswerService {

    private final IUserAnswerRepository userAnswerRepository;

    public UserAnswerServiceImpl(IUserAnswerRepository userAnswerRepository) {
        this.userAnswerRepository = userAnswerRepository;
    }

    /**
     * Finds all user answers by form ID with pagination.
     *
     * @param formId The form ID.
     * @param pageable Pagination information.
     * @return A page of user answers related to the form.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserAnswer> findAllByForm(Long formId, Pageable pageable) {
        return userAnswerRepository.findAllByForm_Id(formId, pageable);
    }

    /**
     * Finds all user answers by user ID with pagination.
     *
     * @param userId The user ID.
     * @param pageable Pagination information.
     * @return A page of user answers related to the user.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserAnswer> findAllByUser(Long userId, Pageable pageable) {
        return userAnswerRepository.findAllByUser_Id(userId, pageable);
    }

    /**
     * Finds all user answers by question ID with pagination.
     *
     * @param questionId The question ID.
     * @param pageable Pagination information.
     * @return A page of user answers related to the question.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserAnswer> findAllByQuestion(Long questionId, Pageable pageable) {
        return userAnswerRepository.findAllByQuestion_Id(questionId, pageable);
    }

    /**
     * Finds all user answers by answer ID with pagination.
     *
     * @param answerId The answer ID.
     * @param pageable Pagination information.
     * @return A page of user answers related to the answer.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserAnswer> findAllByAnswer(Long answerId, Pageable pageable) {
        return userAnswerRepository.findAllByAnswer_Id(answerId, pageable);
    }

    /**
     * Finds a user answer by its ID.
     *
     * @param id The user answer ID.
     * @return The found user answer.
     * @throws NoSuchElementException if the user answer does not exist.
     */
    @Override
    @Transactional(readOnly = true)
    public UserAnswer findById(Long id) throws ElementNotFoundException {
        return userAnswerRepository.findById(id).orElseThrow(
                () -> new ElementNotFoundException("User Answer not found."));
    }

    /**
     * Finds all user answers with pagination.
     *
     * @param pageable Pagination information.
     * @return A page of all user answers.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserAnswer> findAll(Pageable pageable) {
        return userAnswerRepository.findAll(pageable);
    }

    /**
     * Saves a new user answer.
     *
     * @param userAnswer The user answer to save.
     * @return The saved user answer.
     * @throws Exception if an unexpected error occurs during saving.
     */
    @Override
    @Transactional
    public UserAnswer save(UserAnswer userAnswer) throws Exception {
        try {
            return userAnswerRepository.save(userAnswer);
        } catch (ServiceException ex) {
            throw new ServiceException("Unexpected Error While Saving");
        } catch (Exception ex) {
            throw new Exception("Unexpected Error While Saving", ex);
        }
    }

    /**
     * Updates an existing user answer by ID.
     *
     * @param id The ID of the user answer to update.
     * @param userAnswer The new user answer data.
     * @return The updated user answer.
     * @throws Exception if an unexpected error occurs during updating.
     */
    @Override
    @Transactional
    public UserAnswer update(Long id, UserAnswer userAnswer) throws Exception {
        UserAnswer originalUserAnswer = findById(id);
        try {
            BeanUtils.copyProperties(userAnswer, originalUserAnswer, "id");
            return userAnswerRepository.save(originalUserAnswer);
        } catch (ServiceException ex) {
            throw new ServiceException("Unexpected Error While Updating");
        } catch (Exception ex) {
            throw new Exception("Unexpected Error While Updating", ex);
        }
    }

    /**
     * Deletes a user answer by its ID.
     *
     * @param id The ID of the user answer to delete.
     * @throws Exception if an unexpected error occurs during deletion.
     */
    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        findById(id);
        try {
            userAnswerRepository.deleteById(id);
        } catch (ServiceException ex) {
            throw new ServiceException("Unexpected Error While Deleting");
        } catch (Exception ex) {
            throw new Exception("Unexpected Error While Deleting", ex);
        }
    }
}
