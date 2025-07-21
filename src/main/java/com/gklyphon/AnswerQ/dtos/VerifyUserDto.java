package com.gklyphon.AnswerQ.dtos;

/**
 * DTO for user verify requests.
 *
 * @author JFCiscoHuerta
 * @since 2025-07-19
 */
public class VerifyUserDto {

    private String email;
    private String verificationCode;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
