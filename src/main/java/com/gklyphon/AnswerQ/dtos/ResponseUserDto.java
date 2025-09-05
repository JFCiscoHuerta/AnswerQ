package com.gklyphon.AnswerQ.dtos;

import com.gklyphon.AnswerQ.models.Form;
import com.gklyphon.AnswerQ.models.UserAnswer;

import java.time.LocalDate;
import java.util.Set;

/**
 * DTO for user requests.
 *
 * @author JFCiscoHuerta
 * @since 2025-07-20
 */
public class ResponseUserDto {

    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private LocalDate birthdate;
    private String email;
    private String gender;
    private String phoneNumber;
    private Boolean enabled;

    private Set<Form> forms;
    private Set<UserAnswer> userAnswers;

    public ResponseUserDto() {
    }

    public ResponseUserDto(Long id, String username, String firstname, String lastname, LocalDate birthdate, String email, String gender, String phoneNumber, Boolean enabled, Set<Form> forms, Set<UserAnswer> userAnswers) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.email = email;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.enabled = enabled;
        this.forms = forms;
        this.userAnswers = userAnswers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Form> getForms() {
        return forms;
    }

    public void setForms(Set<Form> forms) {
        this.forms = forms;
    }

    public Set<UserAnswer> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(Set<UserAnswer> userAnswers) {
        this.userAnswers = userAnswers;
    }
}
