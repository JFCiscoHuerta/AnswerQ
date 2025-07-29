package com.gklyphon.AnswerQ.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmailUpdateDto {

    @NotBlank(message = "New email is required")
    @Email
    private String newEmail;

    @NotBlank
    @Email
    private String confirmNewEmail;

    @NotBlank(message = "Password is required")
    private String password;

    public EmailUpdateDto() {
    }

    public EmailUpdateDto(String newEmail, String confirmNewEmail, String password) {
        this.newEmail = newEmail;
        this.confirmNewEmail = confirmNewEmail;
        this.password = password;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public String getConfirmNewEmail() {
        return confirmNewEmail;
    }

    public void setConfirmNewEmail(String confirmNewEmail) {
        this.confirmNewEmail = confirmNewEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
