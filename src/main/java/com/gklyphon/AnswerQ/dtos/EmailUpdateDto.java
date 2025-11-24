package com.gklyphon.AnswerQ.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO used for updating a user's email address.
 *
 * @author JFCiscoHuerta
 * @since 2025-11-23
 */
public class EmailUpdateDto {

    /**
     * New email address.
     */
    @NotBlank(message = "New email is required")
    @Email
    private String newEmail;

    /**
     * Confirmation of the new email address.
     */
    @NotBlank
    @Email
    private String confirmNewEmail;

    /**
     * Current password of the user.
     */
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
