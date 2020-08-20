package com.innopolis.innometrics.agentsgateway.controller;

//import com.github.scribejava.apis.;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.*;
import com.github.scribejava.core.oauth.OAuth10aService;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.innopolis.innometrics.agentsgateway.DTO.*;
import com.innopolis.innometrics.agentsgateway.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/AgentGateway", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT})
public class AgentGateway {
    @Autowired
    AgentconfigService agentconfigService;

    @Autowired
    AgentsHandler agentsHandler;

    @Autowired
    private OAuthService oAuthService;

    @GetMapping("/AgentList")
    public ResponseEntity<AgentListResponse> getAgentList(@RequestParam Integer ProjectId) {
        AgentListResponse response = agentconfigService.getAgentList(ProjectId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/AgentConfiguration")
    public ResponseEntity<AgentConfigResponse> getAgentConfiguration(@RequestParam Integer AgentID,
                                                                     @RequestParam(required = false) String CallType) {
        AgentConfigResponse response = agentconfigService.getAgentConfig(AgentID, CallType);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/projectList")
    public ResponseEntity<ProjectListResponse> getProjectList(@RequestParam Integer AgentID,
                                                              @RequestParam Integer ProjectId,
                                                              UriComponentsBuilder ucBuilder) {

        ProjectListResponse response = null;
        try {
            response = agentsHandler.getProjectList(AgentID, ProjectId);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/connectProject")
    public ResponseEntity<?> getConnectProject(@RequestBody ConnectProjectRequest request,
                                               UriComponentsBuilder ucBuilder) {

        Boolean response = false;
        try {
            response = agentsHandler.getConnectProject(request);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (response)
            return new ResponseEntity<>(response, HttpStatus.OK);
        else
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/projectData")
    public ResponseEntity<RepoDataPerProjectResponse> getProjectData(@RequestParam Integer ProjectId) {

        RepoDataPerProjectResponse response = agentsHandler.getRepoDataPerProject(ProjectId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/me/{AgentId}/{projectid}")
    public void me(HttpServletResponse response, @PathVariable Integer AgentId, @PathVariable Integer projectid, @RequestParam String cb) throws InterruptedException, ExecutionException, IOException {
        String AuthURL =  oAuthService.getAuthorizationURL(AgentId, projectid, cb);

        response.setHeader("Location", AuthURL);
        response.setHeader("Accept", "application/json");
        response.setStatus(302);
    }

    @GetMapping("/OAuth/{AgentId}/{userid}")
    public void OAuth(HttpServletResponse response,
                        @PathVariable Integer AgentId,
                        @PathVariable Integer userid,
                        @RequestParam String oauth_token,
                        @RequestParam String oauth_verifier,
                        @RequestParam String cb) {

        oAuthService.storeToken(AgentId, userid, oauth_verifier, cb);
        //return oauth_verifier;

        response.setHeader("Location", cb);
        response.setHeader("Accept", "application/json");
        response.setStatus(302);
    }


    @GetMapping("/OAuth20/")
    public void OAuth20(HttpServletResponse response,
                          @RequestParam Integer agentid,
                          @RequestParam Integer projectid,
                          @RequestParam String code,
                          @RequestParam String cb) {
        oAuthService.storeToken(agentid, projectid, code, cb);
        //return code;

        response.setHeader("Location", cb);
        response.setHeader("Accept", "application/json");
        response.setStatus(302);
    }
}
