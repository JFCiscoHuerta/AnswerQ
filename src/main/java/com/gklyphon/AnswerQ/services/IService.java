package com.gklyphon.AnswerQ.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IService <T, N> {
    T findById(Long id);
    Page<T> findAll(Pageable pageable);
    T save(N n) throws Exception;
    T update(Long id, N n) throws Exception;
    void delete(Long id) throws Exception;
}
