package com.innopolis.innometrics.agentsgateway.service;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.extractors.OAuth2AccessTokenExtractor;
import com.github.scribejava.core.extractors.TokenExtractor;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.Verb;
import org.springframework.stereotype.Service;

//@Service
public class CustomOAuth2 extends DefaultApi20 {

    private String _AccessTokenEndpoint;
    private String _AuthorizationBaseUrl;

    @Override
    public Verb getAccessTokenVerb() {
        return Verb.POST;
    }
    @Override
    public TokenExtractor<OAuth2AccessToken> getAccessTokenExtractor() {
        return OAuth2AccessTokenExtractor.instance();
    }

    @Override
    public String getAccessTokenEndpoint() {
        return _AccessTokenEndpoint;
    }

    @Override
    protected String getAuthorizationBaseUrl() {
        return _AuthorizationBaseUrl;
    }

    public void set_AccessTokenEndpoint(String _AccessTokenEndpoint) {
        this._AccessTokenEndpoint = _AccessTokenEndpoint;
    }

    public void set_AuthorizationBaseUrl(String _AuthorizationBaseUrl) {
        this._AuthorizationBaseUrl = _AuthorizationBaseUrl;
    }
}
