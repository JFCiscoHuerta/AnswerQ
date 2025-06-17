package com.gklyphon.AnswerQ.repositories;

import com.gklyphon.AnswerQ.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


/**
 * Repository interface for managing {@link User} entities.
 *
 * @author JFCiscoHuerta
 * @since 2025-06-16
 */
public interface IUserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their username.
     *
     * @param username The username to search for.
     * @return An Optional containing the found user, or empty if none found.
     */
    Optional<User> findByUsername(String username);
}
