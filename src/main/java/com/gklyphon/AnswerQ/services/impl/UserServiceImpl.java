package com.gklyphon.AnswerQ.services.impl;

import com.gklyphon.AnswerQ.dtos.ResponseUserDto;
import com.gklyphon.AnswerQ.exceptions.exception.ElementNotFoundException;
import com.gklyphon.AnswerQ.mapper.IMapper;
import com.gklyphon.AnswerQ.models.User;
import com.gklyphon.AnswerQ.models.UserAnswer;
import com.gklyphon.AnswerQ.repositories.IUserRepository;
import com.gklyphon.AnswerQ.services.IUserAnswerService;
import com.gklyphon.AnswerQ.services.IUserService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation for managing {@link User} entities.
 * Implements {@link IUserService} interface.
 *
 * @author JFCiscoHuerta
 * @since  2025-11-23
 */
@Service
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IMapper mapper;

    public UserServiceImpl(IUserRepository userRepository, PasswordEncoder passwordEncoder, IMapper mapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    /**
     * Retrieves a user by its ID and converts it into a {@link ResponseUserDto}.
     *
     * @param id the ID of the user to retrieve
     * @return a DTO representation of the user
     * @throws ElementNotFoundException if no user with the given ID exists
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseUserDto findById(Long id) throws ElementNotFoundException {
        return mapper.fromUserToUserDto(userRepository.findById(id).orElseThrow(
                () -> new ElementNotFoundException("User not found")));
    }

    /**
     * Retrieves a paginated list of all users, converting each entity into a DTO.
     *
     * @param pageable pagination information
     * @return a page of mapped {@link ResponseUserDto} objects
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ResponseUserDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(mapper::fromUserToUserDto);
    }


    /**
     * Saves a new user entity after encoding its password.
     *
     * <p>
     * Method marked as {@link Deprecated} due to architectural changes or
     * replacement by better user creation flows.
     * </p>
     *
     * @param user the user to save
     * @return the saved user as a DTO
     * @throws Exception if an unexpected error occurs
     */
    @Deprecated
    @Override
    @Transactional
    public ResponseUserDto save(User user) throws Exception {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return mapper.fromUserToUserDto(userRepository.save(user));
        } catch (ServiceException ex) {
            throw new ServiceException("Unexpected service error while saving user.", ex);
        } catch (Exception ex) {
            throw new Exception("Unexpected error while saving user.", ex);
        }
    }

    /**
     * Updates an existing user, copying allowed fields and preserving
     * immutable or sensitive fields such as ID and password.
     *
     * <p>
     * Method marked as {@link Deprecated} due to being replaced by more
     * specialized profile update flows.
     * </p>
     *
     * @param id   the ID of the user to update
     * @param user the new values to assign to the user
     * @return the updated user as a DTO
     * @throws Exception if an unexpected error occurs
     */

    @Deprecated
    @Override
    @Transactional
    public ResponseUserDto update(Long id, User user) throws Exception {
        User originalUser = userRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("User not found"));
        try {

            BeanUtils.copyProperties(user, originalUser, "id", "password");
            return mapper.fromUserToUserDto(userRepository.save(originalUser));
        } catch (ServiceException ex) {
            throw new ServiceException("Unexpected service error while updating user.", ex);
        } catch (Exception ex) {
            throw new Exception("Unexpected error while updating user.", ex);
        }
    }

    /**
     * Deletes a user by its ID.
     *
     * @param id the ID of the user to delete
     * @throws Exception if an unexpected error occurs during deletion
     */
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
