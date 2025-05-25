package com.gklyphon.AnswerQ.repositories;

import com.gklyphon.AnswerQ.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<Long, User> {
    Optional<User> findByUsername(String username);
}
