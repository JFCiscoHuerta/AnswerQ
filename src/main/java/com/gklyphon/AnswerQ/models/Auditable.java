package com.gklyphon.AnswerQ.models;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

/**
 * Base class for auditing entities. It automatically manages creation and update timestamps.
 *
 * Classes extending this superclass will inherit auditing fields for tracking entity creation
 * and modification.
 *
 * @author JFCiscoHuerta
 * @since 2025-07-19
 *
 */
@MappedSuperclass
public class Auditable {

    /**
     * The date and time when the entity was created.
     */
    @Column(updatable = false)
    private LocalDateTime createdAt;

    /**
     * The date and time when the entity was last updated.
     */
    private LocalDateTime updatedAt;

    /**
     * Initializes the creation and update timestamps before persisting a new entity.
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
    /**
     * Updates the modification timestamp before updating an existing entity.
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
