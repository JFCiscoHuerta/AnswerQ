package com.gklyphon.AnswerQ.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@Builder
@Getter
@Setter
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Form {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private boolean isEnabled;
    private String pin;

    @OneToMany(mappedBy = "form", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Question> questions;
}
