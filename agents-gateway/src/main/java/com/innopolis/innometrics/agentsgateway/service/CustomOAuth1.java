package com.innopolis.innometrics.agentsgateway.service;

import com.github.scribejava.core.builder.api.DefaultApi10a;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth1 extends DefaultApi10a {

    private String _RequestTokenEndpoint;
    private String _AccessTokenEndpoint;
    private String _AuthorizationBaseUrl;
    public CustomOAuth1() {
    }

    @Override
    public String getRequestTokenEndpoint() {
        return _RequestTokenEndpoint;
    }

    @Override
    public String getAccessTokenEndpoint() {
        return _AccessTokenEndpoint;
    }

    @Override
    protected String getAuthorizationBaseUrl() {
        return _AuthorizationBaseUrl;
    }

    public void set_RequestTokenEndpoint(String _RequestTokenEndpoint) {
        this._RequestTokenEndpoint = _RequestTokenEndpoint;
    }

    public void set_AccessTokenEndpoint(String _AccessTokenEndpoint) {
        this._AccessTokenEndpoint = _AccessTokenEndpoint;
    }

    public void set_AuthorizationBaseUrl(String _AuthorizationBaseUrl) {
        this._AuthorizationBaseUrl = _AuthorizationBaseUrl;
    }


    private static class InstanceHolder {
        private static final CustomOAuth1 INSTANCE = new CustomOAuth1();
    }

    public static void init() {
        //return InstanceHolder.INSTANCE;
    }

    public static CustomOAuth1 instance() {
        return InstanceHolder.INSTANCE;
    }

}
