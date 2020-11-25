package com.innopolis.innometrics.agentsgateway.service;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.*;
import com.github.scribejava.core.oauth.OAuth10aService;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.innopolis.innometrics.agentsgateway.entity.Agentconfig;
import com.innopolis.innometrics.agentsgateway.entity.Agentsxproject;
import com.innopolis.innometrics.agentsgateway.repository.AgentconfigRepository;
import com.innopolis.innometrics.agentsgateway.repository.AgentsxprojectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Service
public class OAuthImp implements OAuthService {

    @Value("${innometrics.oauth1a.default-callback}")
    private String DEFAULT_CALLBACK1a;
    @Value("${innometrics.oauth20.default-callback}")
    private String DEFAULT_CALLBACK20;
    @Value("${innometrics.oauthSimple.default-callback}")
    private String DEFAULT_CALLBACKSimple;

    @Autowired
    AgentconfigRepository agentconfigRepository;

    @Autowired
    AgentsxprojectRepository agentsxprojectRepository;

    @Override
    public String getAuthorizationURL(Integer agentid, Integer projectid, String cb) {
        Agentconfig config = agentconfigRepository.findByAgentid(agentid);
        switch (config.getAuthenticationmethod()) {
            case "OAuth1a":
                return getAuthorizationURL1a(config, projectid, cb);
            //break;
            case "OAuth20":
            case "OAuth20J":
                return getAuthorizationURL20(config, projectid, cb);
            //break;
            case "Simple":
                return getAuthorizationURLSimple(config, projectid, cb);
            default:
        }
        return null;
    }

    @Override
    public String storeToken(Integer agentid, Integer projectId, String oauth_verifier, String cb) {
        Agentconfig config = agentconfigRepository.findByAgentid(agentid);
        String token = "";
        switch (config.getAuthenticationmethod()) {
            case "OAuth1a":
                token = getToken1a(config, projectId, oauth_verifier);
                break;
            case "OAuth20":
                token = getToken20(config, projectId, oauth_verifier, cb);
                break;
            case  "OAuth20J":
                token = getToken20j(config, projectId, oauth_verifier, cb);
                break;
            case  "Simple":
                token = oauth_verifier;
                break;
            default:
        }
        Agentsxproject newAgent = new Agentsxproject();
        newAgent.setAgentid(config.getAgentid());
        newAgent.setKey(config.getApikey());
        newAgent.setToken(token);
        newAgent.setProjectid(projectId);
        newAgent.setIsactive("Y");
        agentsxprojectRepository.save(newAgent);

        return token;
    }

    private String getAuthorizationURL1a(Agentconfig config, Integer projectid, String cb) {
        CustomOAuth1 client = new CustomOAuth1(); //CustomOAuth1.instance();
        client.set_AccessTokenEndpoint(config.getAccesstokenendpoint());
        client.set_AuthorizationBaseUrl(config.getAuthorizationbaseurl());
        client.set_RequestTokenEndpoint(config.getRequesttokenendpoint());

        try {
            OAuth10aService service = new ServiceBuilder(config.getApikey()).debug()
                    .apiSecret(config.getApisecret())
                    .callback(DEFAULT_CALLBACK1a + config.getAgentid() + "/" + projectid + "?cb=" + cb)
                    .userAgent("InnoMetrics")
                    .build(client);
            OAuth1RequestToken requestToken = service.getRequestToken();
            return service.getAuthorizationUrl(requestToken);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getAuthorizationURL20(Agentconfig config, Integer projectid, String cb) {
        CustomOAuth2 client = new CustomOAuth2(); //CustomOAuth1.instance();
        client.set_AccessTokenEndpoint(config.getAccesstokenendpoint());
        client.set_AuthorizationBaseUrl(config.getAuthorizationbaseurl());

        try {
            OAuth20Service service = new ServiceBuilder(config.getApikey()).debug()
                    .apiSecret(config.getApisecret())
                    .callback(DEFAULT_CALLBACK20 + "?agentid=" + config.getAgentid() + "&projectid=" + projectid + "&cb=" + cb)
                    .userAgent("InnoMetrics")
                    .build(client);
            return service.getAuthorizationUrl();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private String getAuthorizationURLSimple(Agentconfig config, Integer projectid, String cb) {
        try {
            String Callback = DEFAULT_CALLBACKSimple + "?agentid=" + config.getAgentid() + "&projectid=" + projectid + "&cb=" + cb;
            return config.getAuthorizationbaseurl() + "?callback=" + Callback;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getToken1a(Agentconfig config, Integer projectid, String oauth_verifier) {
        CustomOAuth1 client = new CustomOAuth1();
        client.set_AccessTokenEndpoint(config.getAccesstokenendpoint());
        client.set_AuthorizationBaseUrl(config.getAuthorizationbaseurl());
        client.set_RequestTokenEndpoint(config.getRequesttokenendpoint());

        try {
            OAuth10aService service = new ServiceBuilder(config.getApikey()).debug()
                    .apiSecret(config.getApisecret())
                    .callback(DEFAULT_CALLBACK1a + config.getAgentid() + "/" + projectid)
                    .userAgent("InnoMetrics")
                    .build(client);

            OAuth1RequestToken requestToken = service.getRequestToken();
            OAuth1AccessToken accessToken = service.getAccessToken(requestToken, oauth_verifier);

            return accessToken.getToken();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getToken20(Agentconfig config, Integer projectId, String oauth_verifier, String cb) {
        CustomOAuth2 client = new CustomOAuth2();
        client.set_AccessTokenEndpoint(config.getAccesstokenendpoint());
        client.set_AuthorizationBaseUrl(config.getAuthorizationbaseurl());

        try {


            OAuth20Service service = new ServiceBuilder(config.getApikey()).debug()
                    .apiSecret(config.getApisecret())
                    .defaultScope("api")
                    //.callback(DEFAULT_CALLBACK20)
                    .callback(DEFAULT_CALLBACK20 + "?agentid=" + config.getAgentid() + "&projectid=" + projectId + "&cb=" + cb)
                    .userAgent("InnoMetrics")
                    .build(client);

            OAuth2AccessToken token = service.getAccessToken(oauth_verifier);

            return token.getAccessToken();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getToken20j(Agentconfig config, Integer projectId, String oauth_verifier, String cb) {
        CustomOAuth2J client = new CustomOAuth2J();
        client.set_AccessTokenEndpoint(config.getAccesstokenendpoint());
        client.set_AuthorizationBaseUrl(config.getAuthorizationbaseurl());

        try {
            OAuth20Service service = new ServiceBuilder(config.getApikey()).debug()
                    .apiSecret(config.getApisecret())
                    .defaultScope("api")
                    .callback(DEFAULT_CALLBACK20 + "?agentid=" + config.getAgentid() + "&projectid=" + projectId + "&cb=" + cb)
                    .userAgent("InnoMetrics")
                    .build(client);

            OAuth2AccessToken token = service.getAccessToken(oauth_verifier);

            return token.getAccessToken();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
