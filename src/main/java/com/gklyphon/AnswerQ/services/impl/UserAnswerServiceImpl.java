package com.gklyphon.AnswerQ.services.impl;

import com.gklyphon.AnswerQ.models.*;
import com.gklyphon.AnswerQ.repositories.IUserAnswerRepository;
import com.gklyphon.AnswerQ.services.IUserAnswerService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserAnswerServiceImpl implements IUserAnswerService {

    private final IUserAnswerRepository userAnswerRepository;

    public UserAnswerServiceImpl(IUserAnswerRepository userAnswerRepository) {
        this.userAnswerRepository = userAnswerRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserAnswer> findAllByForm(Long formId, Pageable pageable) {
        return userAnswerRepository.findAllByForm_Id(formId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserAnswer> findAllByUser(Long userId, Pageable pageable) {
        return userAnswerRepository.findAllByUser_Id(userId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserAnswer> findAllByQuestion(Long questionId, Pageable pageable) {
        return userAnswerRepository.findAllByQuestion_Id(questionId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserAnswer> findAllByAnswer(Long answerId, Pageable pageable) {
        return userAnswerRepository.findAllByAnswer_Id(answerId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public UserAnswer findById(Long id) {
        return userAnswerRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserAnswer> findAll(Pageable pageable) {
        return userAnswerRepository.findAll(pageable);
    }

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
