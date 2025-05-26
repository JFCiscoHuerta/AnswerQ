package com.gklyphon.AnswerQ.services.impl;

import com.gklyphon.AnswerQ.models.Form;
import com.gklyphon.AnswerQ.models.User;
import com.gklyphon.AnswerQ.repositories.IFormRepository;
import com.gklyphon.AnswerQ.services.IFormService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FormServiceImpl implements IFormService {

    private final IFormRepository formRepository;

    public FormServiceImpl(IFormRepository formRepository) {
        this.formRepository = formRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Form> findAllByUser(User user, Pageable pageable) {
        return formRepository.findAllByUser(user, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Form findById(Long id) {
        return formRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Form> findAll(Pageable pageable) {
        return formRepository.findAll(pageable);
    }

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
