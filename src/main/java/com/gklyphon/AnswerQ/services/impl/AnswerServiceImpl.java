package com.gklyphon.AnswerQ.services.impl;

import com.gklyphon.AnswerQ.models.Answer;
import com.gklyphon.AnswerQ.repositories.IAnswerRepository;
import com.gklyphon.AnswerQ.services.IAnswerService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

/**
 * Service implementation for managing {@link Answer} entities.
 * Implements {@link IAnswerService} interface.
 *
 * @author JFCiscoHuerta
 * @date 2025-06-16
 */
@Service
public class AnswerServiceImpl implements IAnswerService {

    private final IAnswerRepository answerRepository;

    public AnswerServiceImpl(IAnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    /**
     * Finds answers by question ID with pagination.
     *
     * @param questionId The ID of the question.
     * @param pageable Pagination information.
     * @return A page of answers for the question.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Answer> findAllByQuestion_Id(Long questionId, Pageable pageable) {
        return answerRepository.findAllByQuestion_Id(questionId, pageable);
    }

    /**
     * Finds an answer by its ID.
     *
     * @param id The ID of the answer.
     * @return The answer if found.
     * @throws NoSuchElementException if not found.
     */
    @Override
    @Transactional(readOnly = true)
    public Answer findById(Long id) {
        return answerRepository.findById(id).orElseThrow();
    }

    /**
     * Finds all answers with pagination.
     *
     * @param pageable Pagination information.
     * @return A page of answers.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Answer> findAll(Pageable pageable) {
        return answerRepository.findAll(pageable);
    }

    /**
     * Saves a new answer.
     *
     * @param answer The answer to save.
     * @return The saved answer.
     * @throws Exception If an error occurs during saving.
     */
    @Override
    @Transactional
    public Answer save(Answer answer) throws Exception {
        try {
            return answerRepository.save(answer);
        } catch (ServiceException ex) {
            throw new ServiceException("Unexpected Service Error While Saving", ex);
        } catch (Exception ex) {
            throw new Exception("Unexpected Service Error While Saving", ex);
        }
    }

    /**
     * Updates an existing answer by ID.
     *
     * @param id The ID of the answer to update.
     * @param answer The new data for the answer.
     * @return The updated answer.
     * @throws Exception If an error occurs during update.
     */
    @Override
    @Transactional
    public Answer update(Long id, Answer answer) throws Exception {
        Answer originalAnswer = findById(id);
        try {
            BeanUtils.copyProperties(answer, originalAnswer, "id");
            return answerRepository.save(answer);
        } catch (ServiceException ex) {
            throw new ServiceException("Unexpected Service Error While Updating", ex);
        } catch (Exception ex) {
            throw new Exception("Unexpected Service Error While Updating", ex);
        }
    }

    /**
     * Deletes an answer by its ID.
     *
     * @param id The ID of the answer to delete.
     * @throws Exception If an error occurs during deletion.
     */
    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        findById(id);
        try {
            answerRepository.deleteById(id);
        } catch (ServiceException ex) {
            throw new ServiceException("Unexpected Service Error While Deleting", ex);
        } catch (Exception ex) {
            throw new Exception("Unexpected Service Error While Deleting", ex);
        }
    }
}
