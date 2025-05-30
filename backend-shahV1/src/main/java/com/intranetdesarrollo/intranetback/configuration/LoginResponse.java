package com.intranetdesarrollo.intranetback.configuration;

public class LoginResponse {
    private String token;

    private long expiresIn;

    //Constructores
    public LoginResponse(String token, long expiresIn) {
        this.token = token;
        this.expiresIn = expiresIn;
    }

    public LoginResponse() {
    }

    public String getToken() {
        return token;
    }

    // Getters and setters...
    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }
}