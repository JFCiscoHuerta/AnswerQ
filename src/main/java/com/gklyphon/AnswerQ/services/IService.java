package com.gklyphon.AnswerQ.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Generic service interface for managing entities.
 *
 * Provides basic operations like find, save, update, and delete.
 *
 * @param <T> The type of the entity.
 * @param <N> The type of the input data for saving or updating the entity.
 *
 * @author JFCiscoHuerta
 * @since 2025-06-16
 */
public interface IService <T, N> {

    /**
     * Finds an entity by its ID.
     *
     * @param id The ID of the entity.
     * @return The found entity.
     */
    T findById(Long id);

    /**
     * Gets a paginated list of all entities.
     *
     * @param pageable Pagination information.
     * @return A page of entities.
     */
    Page<T> findAll(Pageable pageable);

    /**
     * Saves a new entity using the provided data.
     *
     * @param n The data to create the entity.
     * @return The saved entity.
     * @throws Exception If an error occurs during save.
     */
    T save(N n) throws Exception;

    /**
     * Updates an existing entity by ID using the provided data.
     *
     * @param id The ID of the entity to update.
     * @param n The new data for the entity.
     * @return The updated entity.
     * @throws Exception If an error occurs during update.
     */
    T update(Long id, N n) throws Exception;

    /**
     * Deletes an entity by its ID.
     *
     * @param id The ID of the entity to delete.
     * @throws Exception If an error occurs during deletion.
     */
    void delete(Long id) throws Exception;
}
