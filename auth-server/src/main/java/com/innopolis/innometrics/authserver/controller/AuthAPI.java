package com.innopolis.innometrics.authserver.controller;

import com.innopolis.innometrics.authserver.DTO.UserRequest;
import com.innopolis.innometrics.authserver.config.JwtToken;
import com.innopolis.innometrics.authserver.entitiy.User;
import com.innopolis.innometrics.authserver.service.RoleService;
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


    @Autowired
    private RoleService roleService;

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

        if (roleService.findByName(user.getRole())== null) {
            throw new ValidationException("No such role");
        }

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
        myUser.setRole(roleService.findByName(user.getRole()));
        myUser.setIsactive("Y");

        myUser = userService.save(myUser);
        System.out.println("New user saved...");

        return new ResponseEntity<>(userService.fromUserToUserRequest(myUser), HttpStatus.CREATED);
    }

    @PutMapping("/User")
    public ResponseEntity<UserRequest> updateUser(@RequestBody  UserRequest user, @RequestHeader(required = true) String Token) {
        if (user != null) {
            User myUser  = userService.findByEmail(user.getEmail());

            if (roleService.findByName(user.getRole())== null) {
                throw new ValidationException("No such role");
            }

            if(myUser != null){
                String UserName = Token != null ? jwtToken.getUsernameFromToken(Token) : "API";

                myUser.setEmail(user.getEmail());
                myUser.setName(user.getName());
                myUser.setSurname(user.getSurname());

                myUser.setBirthday(user.getBirthday());
                myUser.setGender(user.getGender());
                myUser.setFacebook_alias(user.getFacebook_alias());
                myUser.setTelegram_alias(user.getTelegram_alias());
                myUser.setTwitter_alias(user.getTwitter_alias());
                myUser.setLinkedin_alias(user.getLinkedin_alias());

                myUser.setUpdateby(UserName);
                myUser.setLastupdate(new Date());
                myUser.setIsactive(user.getIsactive());
                myUser.setConfirmed_at(user.getConfirmed_at());
                myUser.setRole(roleService.findByName(user.getRole()));

                myUser = userService.save(myUser);

                return ResponseEntity.ok(userService.fromUserToUserRequest(myUser));
            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else
            throw new ValidationException("Not enough data provided");
    }


    @PostMapping("/User/{UserName}")
    public ResponseEntity<Boolean> updateUserPassword(@PathVariable String UserName, @RequestBody  String Password, @RequestHeader(required = true) String Token) {
        if (UserName != null) {
            User myUser  = userService.findByEmail(UserName);

            if(myUser != null){

                myUser.setPassword(new BCryptPasswordEncoder().encode(Password));

                myUser = userService.save(myUser);

                return ResponseEntity.ok(true);
            }
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        } else
            throw new ValidationException("Not enough data provided");
    }

    @GetMapping("/User/{UserName}")
    public ResponseEntity<UserRequest> getUserByName(@PathVariable String UserName) {
        if (!UserName.isEmpty()) {
            User userDetails = userService.findByEmail(UserName);

            if(userDetails == null)
                return new ResponseEntity<>(new UserRequest(), HttpStatus.NO_CONTENT);

            UserRequest myUser = userService.fromUserToUserRequest(userDetails);

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


    @PostMapping("/User/{UserName}/reset")
    public ResponseEntity<Boolean> sendTemporalToken(@PathVariable String UserName,@RequestParam(required = true) String BackUrl, @RequestHeader(required = true) String Token){
        if (UserName != null) {
            User myUser  = userService.findByEmail(UserName);

            if(myUser != null){

                userService.sendRessetPassordEmail(UserName, BackUrl);

                return ResponseEntity.ok(true);
            }
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        } else
            throw new ValidationException("Not enough data provided");
    }

    // here token is temporal for validation of password reset
    @GetMapping("/User/{UserName}/validate")
    public ResponseEntity<Boolean> validateTemporalToken(@PathVariable String UserName, @RequestParam(required = true) String TemporalToken){
        if (UserName != null) {
            User myUser  = userService.findByEmail(UserName);

            if(myUser != null && TemporalToken != null && TemporalToken != ""){

                return ResponseEntity.ok(userService.checkTemporalToken(UserName,TemporalToken));
            }
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        } else
            throw new ValidationException("Not enough data provided");
    }
}
