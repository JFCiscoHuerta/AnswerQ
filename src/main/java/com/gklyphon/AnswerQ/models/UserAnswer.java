package com.gklyphon.AnswerQ.models;

import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * This class represents a user's answer to a question in a form.
 * It connects a user, a form, a question, and an optional selected answer.
 *
 * @author JFCiscoHuerta
 * @since 2025-06-16
 */
@Entity
@Table
public class UserAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The form that contains the question
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_id", nullable = false)
    private Form form;

    // The specific question answered
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    // The selected answer (can be null if it's a text question)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id")
    private Answer answer;

    // The user who answered
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDate answeredAt;

    public UserAnswer() {
    }

    public UserAnswer(Long id, Form form, Question question, Answer answer, User user, LocalDate answeredAt) {
        this.id = id;
        this.form = form;
        this.question = question;
        this.answer = answer;
        this.user = user;
        this.answeredAt = answeredAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getAnsweredAt() {
        return answeredAt;
    }

    public void setAnsweredAt(LocalDate answeredAt) {
        this.answeredAt = answeredAt;
    }
}
