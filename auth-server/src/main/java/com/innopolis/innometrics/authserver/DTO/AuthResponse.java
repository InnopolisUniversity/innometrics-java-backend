package com.innopolis.innometrics.authserver.DTO;

public class AuthResponse {
    private static final long serialVersionUID = -8091879091924046844L;

    private final String jwttoken;

    public AuthResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public String getToken() {
        return this.jwttoken;
    }
}
