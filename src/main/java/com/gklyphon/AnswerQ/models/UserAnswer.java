package com.gklyphon.AnswerQ.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table
public class UserAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_id", nullable = false)
    private Form form;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id")
    private Answer answer;

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
