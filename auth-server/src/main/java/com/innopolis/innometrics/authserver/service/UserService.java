package com.innopolis.innometrics.authserver.service;


import com.innopolis.innometrics.authserver.DTO.UserListRequest;
import com.innopolis.innometrics.authserver.DTO.UserListResponse;
import com.innopolis.innometrics.authserver.DTO.UserResponse;
import com.innopolis.innometrics.authserver.repository.UserRepository;
import com.innopolis.innometrics.authserver.entitiy.User;
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
            temp.setRole(u.getRole());
//            temp.setPermissions(u.getRole().getPermissions());
            response.getUserList().add(temp);
        }
        return response;
    }
}
