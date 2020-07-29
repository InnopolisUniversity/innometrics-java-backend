package com.innopolis.innometrics.authserver.service;


import com.innopolis.innometrics.authserver.DTO.*;
import com.innopolis.innometrics.authserver.entitiy.Permission;
import com.innopolis.innometrics.authserver.repository.RoleRepository;
import com.innopolis.innometrics.authserver.repository.UserRepository;
import com.innopolis.innometrics.authserver.entitiy.User;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                new ArrayList<>());
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public UserListResponse getActiveUsers(String request) {

        request = request == "" ? null : request;
        List<User> result = userRepository.findAllActive(request);

        UserListResponse response = new UserListResponse();
        for (User u : result) {
            UserResponse temp = new UserResponse();
            temp.setName(u.getName());
            temp.setEmail(u.getEmail());
            temp.setSurname(u.getSurname());
            temp.setIsactive(u.getIsactive());
            temp.setRole(u.getRole().getName());
            response.getUserList().add(temp);
        }
        return response;
    }

    public UserRequest fromUserToUserRequest(User myUser){
        UserRequest myUserRq = new UserRequest();
        myUserRq.setName(myUser.getName());
        myUserRq.setSurname(myUser.getSurname());
        myUserRq.setEmail(myUser.getEmail());
        myUserRq.setPassword(myUser.getPassword());
        myUserRq.setIsactive(myUser.getIsactive());
        myUserRq.setConfirmed_at(myUser.getConfirmed_at());
        myUserRq.setRole(myUser.getRole().getName());

        return myUserRq;
    }

    public UserResponse fromUserToUserResponse(User myUser){
        UserResponse myUserRq = new UserResponse();
        myUserRq.setName(myUser.getName());
        myUserRq.setSurname(myUser.getSurname());
        myUserRq.setEmail(myUser.getEmail());
        myUserRq.setIsactive(myUser.getIsactive());
        myUserRq.setRole(myUser.getRole().getName());

        List<PageResponse> pages =  new ArrayList<>();

        for (Permission permission : myUser.getRole().getPermissions()) {
            PageResponse temp = new PageResponse(permission.getPage().getPage(), permission.getPage().getIcon());
            pages.add(temp);
        }
        myUserRq.setPages(pages);

        return myUserRq;
    }
}
