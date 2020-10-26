package com.innopolis.innometrics.authserver.DTO;

import java.sql.Timestamp;
import java.util.Date;

public class TemporalTokenRequest {
    private String email;

    private String temporal_token;

    private Timestamp expirationdate;

    public TemporalTokenRequest(String email, String temporal_token, Timestamp expirationdate) {
        this.email = email;
        this.temporal_token = temporal_token;
        this.expirationdate = expirationdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTemporal_token() {
        return temporal_token;
    }

    public void setTemporal_token(String temporal_token) {
        this.temporal_token = temporal_token;
    }

    public Date getExpirationdate() {
        return expirationdate;
    }

    public void setExpirationdate(Timestamp expirationdate) {
        this.expirationdate = expirationdate;
    }
}
