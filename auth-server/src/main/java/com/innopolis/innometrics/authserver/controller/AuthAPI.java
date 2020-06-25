package com.innopolis.innometrics.authserver.controller;

import com.innopolis.innometrics.authserver.DTO.ProjectListResponse;
import com.innopolis.innometrics.authserver.DTO.ProjectResponse;
import com.innopolis.innometrics.authserver.DTO.UserRequest;
import com.innopolis.innometrics.authserver.config.JwtToken;
import com.innopolis.innometrics.authserver.entitiy.Project;
import com.innopolis.innometrics.authserver.entitiy.User;
import com.innopolis.innometrics.authserver.service.UserService;
import com.innopolis.innometrics.authserver.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.Date;

@RestController
//@CrossOrigin
@RequestMapping("AuthAPI")
public class AuthAPI {

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private UserService userService;

    @PostMapping("/User")
    public ResponseEntity<UserRequest> CreateUser(@RequestBody UserRequest user, @RequestHeader(required = false) String Token) {
        System.out.println("Create user method started");
        String UserName = "API";

        if (user == null)
            throw new ValidationException("Not enough data provided");

        if (user.getEmail() == null || user.getName() == null || user.getSurname() == null || user.getPassword() == null)
            throw new ValidationException("Not enough data provided");

        if (userService.existsByEmail(user.getEmail()))
            throw new ValidationException("Username already existed");

        if (Token != null)
            UserName = jwtToken.getUsernameFromToken(Token);


        System.out.println("New user creation...");
        User myUser = new User();
        myUser.setEmail(user.getEmail());
        myUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        myUser.setName(user.getName());
        myUser.setSurname(user.getSurname());
        myUser.setCreationdate(new Date());
        myUser.setCreatedby(UserName);
        myUser.setIsactive("Y");

        userService.save(myUser);
        System.out.println("New user saved...");

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/User")
    public ResponseEntity<UserRequest> updateUser(@RequestBody  UserRequest user, @RequestHeader(required = true) String Token) {
        if (user != null) {
            User myUser  = userService.findByEmail(user.getEmail());
            if(myUser != null){
                String UserName = Token != null ? jwtToken.getUsernameFromToken(Token) : "API";

                myUser.setEmail(user.getEmail());
                myUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
                myUser.setName(user.getName());
                myUser.setSurname(user.getSurname());
                myUser.setUpdateby(UserName);
                myUser.setLastupdate(new Date());
                myUser.setIsactive(user.getIsactive());
                myUser.setConfirmed_at(user.getConfirmed_at());

                userService.save(myUser);

                return ResponseEntity.ok(user);
            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else
            throw new ValidationException("Not enough data provided");
    }

    @GetMapping("/User/{UserName}")
    public ResponseEntity<UserRequest> getUserByName(@PathVariable String UserName) {
        if (!UserName.isEmpty()) {
            User userDetails = userService.findByEmail(UserName);
            UserRequest myUser = new UserRequest();

            if(userDetails == null)
                return new ResponseEntity<>(myUser, HttpStatus.NO_CONTENT);


            myUser.setEmail(userDetails.getEmail());
            myUser.setPassword(userDetails.getPassword());
            myUser.setName(userDetails.getName());
            myUser.setSurname(userDetails.getSurname());
            myUser.setConfirmed_at(userDetails.getConfirmed_at());
            myUser.setIsactive(userDetails.getIsactive());
            return ResponseEntity.ok(myUser);
        } else
            throw new ValidationException("Not enough data provided");
    }

    @GetMapping("/Validate")
    public ResponseEntity<UserDetails> validateToken(@RequestHeader(required = false) String Token) {
        String UserName = jwtToken.getUsernameFromToken(Token);
        if (!UserName.isEmpty()) {
            UserDetails userDetails = userService.loadUserByUsername(UserName);
            return new ResponseEntity<>(userDetails, HttpStatus.OK);
        } else
            throw new ValidationException("Not enough data provided");
    }
}
