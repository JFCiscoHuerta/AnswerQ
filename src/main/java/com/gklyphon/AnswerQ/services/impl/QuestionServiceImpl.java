package com.gklyphon.AnswerQ.services.impl;

import com.gklyphon.AnswerQ.models.Form;
import com.gklyphon.AnswerQ.models.Question;
import com.gklyphon.AnswerQ.repositories.IQuestionRepository;
import com.gklyphon.AnswerQ.services.IQuestionService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuestionServiceImpl implements IQuestionService {

    private final IQuestionRepository questionRepository;

    public QuestionServiceImpl(IQuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Question> findAllByForm(Form form, Pageable pageable) {
        return questionRepository.findAllByForm(form, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Question findById(Long id) {
        return questionRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Question> findAll(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

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
