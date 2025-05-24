package com.gklyphon.AnswerQ.models;


import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private boolean isCorrect;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;
}
