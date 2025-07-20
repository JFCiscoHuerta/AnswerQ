package com.gklyphon.AnswerQ.models;

import jakarta.persistence.*;

import java.util.Set;

/**
 * This class represents a question that belongs to a form.
 * Questions are part of a form and help collect user responses.
 *
 * @author JFCiscoHuerta
 * @since 2025-06-16
 */
@Entity
@Table
public class Question extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private boolean isRequired;

    // The form this question belongs to
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_id", nullable = false)
    private Form form;

    // List of answers related to this question
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Answer> answers;
}
