package com.innopolis.innometrics.restapi.service;

import com.innopolis.innometrics.restapi.DTO.UserRequest;
import com.innopolis.innometrics.restapi.entitiy.User;
import com.innopolis.innometrics.restapi.repository.UserRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

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

    @HystrixCommand(commandKey = "findByEmail", fallbackMethod = "findByEmailFallback")
    public User findByEmail(String email) {
        UserRequest user = new UserRequest();
        String uri = baseURL + "/" + email;

        ResponseEntity<UserRequest> response = null;

        HttpEntity<UserRequest> entity = new HttpEntity<>(null);
        response = restTemplate.exchange(uri, HttpMethod.GET, entity, UserRequest.class);

        HttpStatus status = response.getStatusCode();
        user = response.getBody();

        if (status == HttpStatus.NO_CONTENT) return null;

        User myUser = new User();
        myUser.setName(user.getName());
        myUser.setSurname(user.getSurname());
        myUser.setEmail(user.getEmail());
        myUser.setPassword(user.getPassword());
        myUser.setIsactive(user.getIsactive());
        myUser.setConfirmed_at(user.getConfirmed_at());

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

        return myUser;
    }

    @HystrixCommand(commandKey = "update", fallbackMethod = "updateFallback")
    public Boolean update(User myUser) {

        String uri = baseURL;// + "/User";

        UserRequest myUserRq = new UserRequest();
        myUserRq.setName(myUser.getName());
        myUserRq.setSurname(myUser.getSurname());
        myUserRq.setEmail(myUser.getEmail());
        myUserRq.setPassword(myUser.getPassword());
        myUserRq.setIsactive(myUser.getIsactive());
        myUserRq.setConfirmed_at(myUser.getConfirmed_at());

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

    @HystrixCommand(commandKey = "create", fallbackMethod = "createFallback")
    public boolean create(User myUser) {

        String uri = baseURL;//"/User";

        UserRequest myUserRq = new UserRequest();
        myUserRq.setName(myUser.getName());
        myUserRq.setSurname(myUser.getSurname());
        myUserRq.setEmail(myUser.getEmail());
        myUserRq.setPassword(myUser.getPassword());
        myUserRq.setIsactive(myUser.getIsactive());
        myUserRq.setConfirmed_at(myUser.getConfirmed_at());

        HttpEntity<UserRequest> entity = new HttpEntity<>(myUserRq);
        ResponseEntity<UserRequest> response = restTemplate.exchange(uri, HttpMethod.POST, entity, UserRequest.class);

        HttpStatus status = response.getStatusCode();
        UserRequest restCall = response.getBody();

        if (status == HttpStatus.CREATED) {
            return true;
        }
        return false;
    }

    public boolean createFallback(User myUser) {
        return false;
    }
}
