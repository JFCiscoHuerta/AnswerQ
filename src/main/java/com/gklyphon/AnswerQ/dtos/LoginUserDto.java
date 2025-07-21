package com.gklyphon.AnswerQ.dtos;

/**
 * DTO for user login requests.
 *
 * @author JFCiscoHuerta
 * @since 2025-07-19
 */
public class LoginUserDto {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
