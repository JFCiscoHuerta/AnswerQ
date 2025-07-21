package com.gklyphon.AnswerQ.services;

import com.gklyphon.AnswerQ.exceptions.exception.ElementNotFoundException;
import com.gklyphon.AnswerQ.models.User;

/**
 * Service interface for managing {@link User} entities.
 * Extends the generic {@link IService} for basic operations.
 *
 * @author JFCiscoHuerta
 * @since 2025-06-21
 */
public interface IUserService extends IService<User, User> {

}
