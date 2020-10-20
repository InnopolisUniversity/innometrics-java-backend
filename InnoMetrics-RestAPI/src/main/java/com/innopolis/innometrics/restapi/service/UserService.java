package com.innopolis.innometrics.restapi.service;

import com.innopolis.innometrics.restapi.DTO.*;
import com.innopolis.innometrics.restapi.entitiy.Page;
import com.innopolis.innometrics.restapi.entitiy.Permission;
import com.innopolis.innometrics.restapi.entitiy.Role;
import com.innopolis.innometrics.restapi.entitiy.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    private static Logger LOG = LogManager.getLogger();

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RoleService roleService;

    private String baseURL = "http://INNOMETRICS-AUTH-SERVER/AuthAPI/User";

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findByEmail(email);

        if (user == null)
            throw new UsernameNotFoundException("User not found with email: " + email);

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                new ArrayList<>());
    }

    public boolean existsByEmail(String email) {
        User user = findByEmail(email);
        return user != null;
    }

    @HystrixCommand( commandKey = "findByEmail", fallbackMethod = "findByEmailFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")
    })
    public User findByEmail(String email) {
        UserRequest user = new UserRequest();
        String uri = baseURL + "/" + email;

        ResponseEntity<UserRequest> response = null;

        HttpEntity<UserRequest> entity = new HttpEntity<>(null);
        response = restTemplate.exchange(uri, HttpMethod.GET, entity, UserRequest.class);

        HttpStatus status = response.getStatusCode();
        user = response.getBody();

        if (status == HttpStatus.NO_CONTENT) return null;

        User myUser = fromUserRequestToUser(user);

        return myUser;
    }


    public User findByEmailFallback(String email) {

        User myUser = new User();
        myUser.setName("Default");
        myUser.setSurname("User");
        myUser.setEmail(email);
        myUser.setPassword("");
        myUser.setIsactive("N");
        myUser.setConfirmed_at(new Date());

        RoleResponse roleResponse = roleService.getRole("DEVELOPER");
        myUser.setRole(roleService.RoleFromRoleResponse(roleResponse));

        return myUser;
    }

    @HystrixCommand( commandKey = "update", fallbackMethod = "updateFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")
    })
    public Boolean update(User myUser) {

        String uri = baseURL;// + "/User";

        UserRequest myUserRq = fromUserToUserRequest(myUser);

        HttpEntity<UserRequest> entity = new HttpEntity<>(myUserRq);
        ResponseEntity<UserRequest> response = restTemplate.exchange(uri, HttpMethod.PUT, entity, UserRequest.class);

        HttpStatus status = response.getStatusCode();
        UserRequest restCall = response.getBody();

        if (status == HttpStatus.CREATED) {
            return true;
        }

        return false;

    }

    public Boolean updateFallback(User myUser) {
        return false;
    }

    @HystrixCommand( commandKey = "create", fallbackMethod = "createFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")
    })
    public boolean create(UserRequest myUserRq) {

        try {
            String uri = baseURL;


            HttpEntity<UserRequest> entity = new HttpEntity<>(myUserRq);
            ResponseEntity<UserRequest> response = restTemplate.exchange(uri, HttpMethod.POST, entity, UserRequest.class);

            HttpStatus status = response.getStatusCode();
            UserRequest restCall = response.getBody();

            if (status == HttpStatus.CREATED) {
                return true;
            }
        }
        catch (Exception ex){
         LOG.warn(ex.getMessage());
        }

        return false;
    }

    public boolean createFallback(UserRequest myUser) {
        return false;
    }


    @HystrixCommand( commandKey = "setRole", fallbackMethod = "setRoleFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000")
    })
    public UserResponse setRole(String userName, String roleName) {
        String uri = "http://INNOMETRICS-AUTH-SERVER/AdminAPI/User/Role" ;

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("RoleName", roleName).queryParam("UserName", userName);

        ResponseEntity<UserResponse> response = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, null, UserResponse.class);
        HttpStatus status = response.getStatusCode();

        return response.getBody();
    }


    public UserResponse setRoleFallback(String userName, String roleName, Throwable exception) {
        LOG.warn("setRoleFallback method used");
        LOG.warn(exception);
        return null;
    }


    private UserRequest fromUserToUserRequest(User myUser){
        UserRequest myUserRq = new UserRequest();
        myUserRq.setName(myUser.getName());
        myUserRq.setSurname(myUser.getSurname());
        myUserRq.setEmail(myUser.getEmail());
        myUserRq.setPassword(myUser.getPassword());

        myUserRq.setBirthday(myUser.getBirthday());
        myUserRq.setGender(myUser.getGender());
        myUserRq.setFacebook_alias(myUser.getFacebook_alias());
        myUserRq.setTelegram_alias(myUser.getTelegram_alias());
        myUserRq.setTwitter_alias(myUser.getTwitter_alias());
        myUserRq.setLinkedin_alias(myUser.getLinkedin_alias());

        myUserRq.setIsactive(myUser.getIsactive());
        myUserRq.setConfirmed_at(myUser.getConfirmed_at());
        myUserRq.setRole(myUser.getRole().getName());

        return myUserRq;
    }

    private User fromUserRequestToUser(UserRequest userRequest){
        User myUser = new User();
        myUser.setName(userRequest.getName());
        myUser.setSurname(userRequest.getSurname());
        myUser.setEmail(userRequest.getEmail());
        myUser.setPassword(userRequest.getPassword());

        myUser.setBirthday(userRequest.getBirthday());
        myUser.setGender(userRequest.getGender());
        myUser.setFacebook_alias(userRequest.getFacebook_alias());
        myUser.setTelegram_alias(userRequest.getTelegram_alias());
        myUser.setTwitter_alias(userRequest.getTwitter_alias());
        myUser.setLinkedin_alias(userRequest.getLinkedin_alias());

        myUser.setIsactive(userRequest.getIsactive());
        myUser.setConfirmed_at(userRequest.getConfirmed_at());

        RoleResponse roleResponse = roleService.getRole(userRequest.getRole());
        myUser.setRole(roleService.RoleFromRoleResponse(roleResponse));

        return myUser;
    }

    public Boolean updatePassword(User myUser, String token) {
        try {
            String uri = baseURL + "/" + myUser.getEmail();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Token", token);

            HttpEntity<String> entity = new HttpEntity<>(myUser.getPassword(), headers);
            try {
                ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.POST, entity, Object.class);

                HttpStatus status = response.getStatusCode();

                return status == HttpStatus.OK;
            } catch (Exception e) {
                LOG.warn(e);
                return false;
            }


//            HttpEntity<String> entity = new HttpEntity<>(myUser);
//            ResponseEntity<Boolean> response = restTemplate.exchange(uri, HttpMethod.POST, entity, UserRequest.class);
//
//            HttpStatus status = response.getStatusCode();
//            UserRequest restCall = response.getBody();
//
//            if (status == HttpStatus.CREATED) {
//                return true;
//            }
        }
        catch (Exception ex){
            LOG.warn(ex.getMessage());
        }

        return false;
    }
}
