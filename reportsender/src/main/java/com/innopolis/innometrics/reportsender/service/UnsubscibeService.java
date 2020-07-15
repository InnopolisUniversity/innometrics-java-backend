package com.innopolis.innometrics.reportsender.service;

import com.innopolis.innometrics.reportsender.model.UserModel;
import com.innopolis.innometrics.reportsender.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UnsubscibeService {

    @Autowired
    public UserRepository userRepository;


    public void setEmailSubscription(String email) {
        Optional<UserModel> user = userRepository.findById(email);
        if (user.isPresent())
            user.get().setEmail_subscription(false);
        userRepository.save(user.get());
    }

}
