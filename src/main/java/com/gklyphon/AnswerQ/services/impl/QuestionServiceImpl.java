package com.gklyphon.AnswerQ.services.impl;

import com.gklyphon.AnswerQ.exceptions.exception.ElementNotFoundException;
import com.gklyphon.AnswerQ.models.Question;
import com.gklyphon.AnswerQ.repositories.IQuestionRepository;
import com.gklyphon.AnswerQ.services.IQuestionService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

/**
 * Service implementation for managing {@link Question} entities.
 * Implements {@link IQuestionService} interface.
 *
 * @author JFCiscoHuerta
 * @since  2025-06-16
 */
@Service
public class QuestionServiceImpl implements IQuestionService {

    private final IQuestionRepository questionRepository;

    public QuestionServiceImpl(IQuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    /**
     * Finds all questions by the form ID with pagination.
     *
     * @param id The form ID.
     * @param pageable Pagination parameters.
     * @return A page of questions belonging to the specified form.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Question> findAllByForm_Id(Long id, Pageable pageable) {
        return questionRepository.findAllByForm_Id(id, pageable);
    }

    /**
     * Finds a question by its ID.
     *
     * @param id The question ID.
     * @return The found question.
     * @throws NoSuchElementException if the question does not exist.
     */
    @Override
    @Transactional(readOnly = true)
    public Question findById(Long id) throws ElementNotFoundException {
        return questionRepository.findById(id).orElseThrow(
                () -> new ElementNotFoundException("Question not found."));
    }

    /**
     * Finds all questions with pagination.
     *
     * @param pageable Pagination parameters.
     * @return A page of questions.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Question> findAll(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    /**
     * Saves a new question.
     *
     * @param question The question to save.
     * @return The saved question.
     * @throws Exception if an unexpected error occurs during saving.
     */
    @Override
    @Transactional
    public Question save(Question question) throws Exception {
        try {
            return questionRepository.save(question);
        } catch (ServiceException ex) {
            throw new ServiceException("Unexpected Service Error While Saving", ex);
        } catch (Exception ex) {
            throw new Exception("Unexpected Error While Saving", ex);
        }
    }

    /**
     * Updates an existing question by its ID.
     *
     * @param id The ID of the question to update.
     * @param question The new question data.
     * @return The updated question.
     * @throws Exception if an unexpected error occurs during updating.
     */
    @Override
    @Transactional
    public Question update(Long id, Question question) throws Exception {
        Question originalQuestion = findById(id);
        try {
            BeanUtils.copyProperties(question, originalQuestion, "id");
            return questionRepository.save(originalQuestion);
        } catch (ServiceException ex) {
            throw new ServiceException("Unexpected Service Error While Updating", ex);
        } catch (Exception ex) {
            throw new Exception("Unexpected Error While Updating", ex);
        }
    }

    /**
     * Deletes a question by its ID.
     *
     * @param id The ID of the question to delete.
     * @throws Exception if an unexpected error occurs during deletion.
     */
    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        findById(id);
        try {
            questionRepository.deleteById(id);
        } catch (ServiceException ex) {
            throw new ServiceException("Unexpected Service Error While Deleting", ex);
        } catch (Exception ex) {
            throw new Exception("Unexpected Error While Deleting", ex);
        }
    }
}
