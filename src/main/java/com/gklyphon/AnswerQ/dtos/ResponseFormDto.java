package com.gklyphon.AnswerQ.dtos;

import com.gklyphon.AnswerQ.models.Question;

import java.util.Set;

/**
 * DTO representing a form response.
 *
 * @author JFCiscoHuerta
 * @since 2025-11-23
 */
public class ResponseFormDto {
    private Long id;
    private String name;
    private boolean isEnabled;
    private String pin;

    private ResponseUserDto responseUserDto;
    private Set<Question> questions;

    public ResponseFormDto() {
    }

    public ResponseFormDto(Long id, String name, boolean isEnabled, String pin, ResponseUserDto responseUserDto, Set<Question> questions) {
        this.id = id;
        this.name = name;
        this.isEnabled = isEnabled;
        this.pin = pin;
        this.responseUserDto = responseUserDto;
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

    public ResponseUserDto getResponseUserDto() {
        return responseUserDto;
    }

    public void setResponseUserDto(ResponseUserDto responseUserDto) {
        this.responseUserDto = responseUserDto;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }
}
