package com.gklyphon.AnswerQ.models;

import jakarta.persistence.*;

import java.util.Set;

/**
 * This class represents a form created by a user.
 * Each form belongs to a specific user.
 *
 * @author JFCiscoHuerta
 * @since 2025-06-16
 */
@Entity
@Table
public class Form extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private boolean isEnabled;
    private String pin;

    // The user owner of the form
    @ManyToOne
    private User user;

    // List of questions in the form
    @OneToMany(mappedBy = "form", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Question> questions;

    public Form() {
    }

    public Form(Long id, String name, boolean isEnabled, String pin, User user, Set<Question> questions) {
        this.id = id;
        this.name = name;
        this.isEnabled = isEnabled;
        this.pin = pin;
        this.user = user;
        this.questions = questions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }
}
