package com.innopolis.innometrics.restapi.DTO;

import java.io.Serializable;

public class AuthResponse  implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;

    private final String jwttoken;

    public AuthResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public String getToken() {
        return this.jwttoken;
    }
}
