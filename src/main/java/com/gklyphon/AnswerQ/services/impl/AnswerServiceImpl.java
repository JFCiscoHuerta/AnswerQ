package com.gklyphon.AnswerQ.services.impl;

import com.gklyphon.AnswerQ.models.Answer;
import com.gklyphon.AnswerQ.models.Question;
import com.gklyphon.AnswerQ.repositories.IAnswerRepository;
import com.gklyphon.AnswerQ.services.IAnswerService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnswerServiceImpl implements IAnswerService {

    private final IAnswerRepository answerRepository;

    public AnswerServiceImpl(IAnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Answer> findAllByQuestion_Id(Long questionId, Pageable pageable) {
        return answerRepository.findAllByQuestion_Id(questionId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Answer findById(Long id) {
        return answerRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Answer> findAll(Pageable pageable) {
        return answerRepository.findAll(pageable);
    }

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
