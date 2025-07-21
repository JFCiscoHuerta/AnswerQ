package com.gklyphon.AnswerQ.services.impl;

import com.gklyphon.AnswerQ.exceptions.exception.ElementNotFoundException;
import com.gklyphon.AnswerQ.models.User;
import com.gklyphon.AnswerQ.repositories.IUserRepository;
import com.gklyphon.AnswerQ.services.IUserService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) throws ElementNotFoundException {
        return userRepository.findById(id).orElseThrow(
                () -> new ElementNotFoundException("User not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public User save(User user) throws Exception {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } catch (ServiceException ex) {
            throw new ServiceException("Unexpected service error while saving user.", ex);
        } catch (Exception ex) {
            throw new Exception("Unexpected error while saving user.", ex);
        }
    }

    @Override
    @Transactional
    public User update(Long id, User user) throws Exception {
        User originalUser = findById(id);
        try {

            BeanUtils.copyProperties(user, originalUser, "id");
            return userRepository.save(originalUser);
        } catch (ServiceException ex) {
            throw new ServiceException("Unexpected service error while updating user.", ex);
        } catch (Exception ex) {
            throw new Exception("Unexpected error while updating user.", ex);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        findById(id);
        try {
            userRepository.deleteById(id);
        } catch (ServiceException ex) {
            throw new ServiceException("Unexpected service error while deleting user.", ex);
        } catch (Exception ex) {
            throw new Exception("Unexpected error while deleting user.", ex);
        }
    }
}
