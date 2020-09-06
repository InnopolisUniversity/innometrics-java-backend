package com.innopolis.innometrics.authserver.service;

import com.innopolis.innometrics.authserver.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    public Boolean existsByEmail(String email, String macAddress){
        return profileRepository.existsByUserEmailAndMacAddress(email,macAddress);
    }

}
