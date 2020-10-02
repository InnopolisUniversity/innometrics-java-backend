package com.innopolis.innometrics.authserver.service;

import com.innopolis.innometrics.authserver.DTO.ProfileRequest;
import com.innopolis.innometrics.authserver.entitiy.Profile;
import com.innopolis.innometrics.authserver.repository.ProfileRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@Component
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    public Boolean existsByEmail(String email, String macAddress){
        return profileRepository.existsByUserEmailAndMacAddress(email,macAddress);
    }

    public Profile findByUid(Integer uid) {
        return profileRepository.findById(uid).orElse(null);
    }


    public ProfileRequest create(ProfileRequest detail){
        Profile entity = new Profile();
        detail.setProfileId(null);
        BeanUtils.copyProperties(detail,entity,getNullPropertyNames(detail));

        entity = profileRepository.saveAndFlush(entity);


        BeanUtils.copyProperties(entity,detail);
        return detail;

    }

    public ProfileRequest findByID(Integer id){
        Profile entity = profileRepository.findById(id).orElse(null);
        assertNotNull(entity,
                "No profile found by id " + id);

        ProfileRequest detail = new ProfileRequest();

        BeanUtils.copyProperties(entity,detail);

        return detail;
    }

    public ProfileRequest findByMacAddress(String macAddress){
        Profile entity = profileRepository.findByMacAddress(macAddress);

        assertNotNull(entity,
                "No profile found by macAdress " + macAddress );

        ProfileRequest detail = new ProfileRequest();

        BeanUtils.copyProperties(entity,detail);

        return detail;

    }

    public ProfileRequest update(ProfileRequest detail){
        Profile entity = profileRepository.findByUserEmailAndMacAddress(
                detail.getUserEmail(),
                detail.getMacAddress()
        );
        assertNotNull(entity,
                "No profile found by user email and macaddress " + detail.getProfileId());
        detail.setProfileId(null);
        BeanUtils.copyProperties(detail,entity,getNullPropertyNames(detail));
        entity=profileRepository.saveAndFlush(entity);
        BeanUtils.copyProperties(entity,detail);

        return detail;
    }


    public void delete(Integer id) {

        Profile entity = profileRepository.findById(id).orElse(null);
        assertNotNull(entity,
                "No profile found by id " + id);


        profileRepository.delete(entity);
    }




    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        Set emptyNames = new HashSet();

        for(java.beans.PropertyDescriptor descriptor : src.getPropertyDescriptors()) {

            if (src.getPropertyValue(descriptor.getName()) == null) {
                emptyNames.add(descriptor.getName());
            }
        }

        String[] result = new String[emptyNames.size()];
        return (String[]) emptyNames.toArray(result);
    }
}
