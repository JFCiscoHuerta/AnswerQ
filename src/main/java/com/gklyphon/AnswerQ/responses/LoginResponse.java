package com.gklyphon.AnswerQ.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the response sent to the client after successful authentication.
 *
 * @author JFCiscoHuerta
 * @since 2025-07-19
 */
public class LoginResponse {

    @JsonProperty("token")
    private String token;

    @JsonProperty("expiresIn")
    public long expiresIn;

    @JsonProperty("verified")
    public boolean verified;

    public LoginResponse(String token, long expiresIn, boolean verified) {
        this.token = token;
        this.expiresIn = expiresIn;
        this.verified = verified;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
