package com.gklyphon.AnswerQ.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO used for updating a user's password.
 *
 * @author JFCiscoHuerta
 * @since 2025-11-24
 */
public class PasswordUpdateDto {

    /**
     * The user's current password.
     */
    @NotBlank(message = "Old password is required")
    private String oldPassword;

    /**
     * The new password requested by the user.
     */
    @NotBlank(message = "New password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String newPassword;

    /**
     * Confirmation of the new password.
     */
    private String confirmNewPassword;

    public PasswordUpdateDto() {
    }

    public PasswordUpdateDto(String oldPassword, String newPassword, String confirmNewPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.confirmNewPassword = confirmNewPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }
}
