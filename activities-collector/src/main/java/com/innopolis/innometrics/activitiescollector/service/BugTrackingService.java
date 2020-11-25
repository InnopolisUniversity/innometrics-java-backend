package com.innopolis.innometrics.activitiescollector.service;

import com.innopolis.innometrics.activitiescollector.DTO.BugTrackingListRequest;
import com.innopolis.innometrics.activitiescollector.DTO.BugTrackingRequest;
import com.innopolis.innometrics.activitiescollector.entity.BugTracking;
import com.innopolis.innometrics.activitiescollector.repository.BugTrackingRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@Service
//@Transactional
public class BugTrackingService {

    @Autowired
    BugTrackingRepository bugTrackingRepository;

    public boolean existsById (Integer id){
        return bugTrackingRepository.existsById(id);
    }

    public BugTrackingRequest create(BugTrackingRequest detail){
        BugTracking entity = new BugTracking();
        detail.setBugId(null);
        detail.setStatus(false);
        BeanUtils.copyProperties(detail,entity,getNullPropertyNames(detail));

        entity = bugTrackingRepository.saveAndFlush(entity);


        BeanUtils.copyProperties(entity,detail);
        return detail;

    }

    public BugTrackingRequest findByID(Integer id){
        BugTracking entity = bugTrackingRepository.findById(id).orElse(null);
        assertNotNull(entity,
                "No bug found by id " + id);

        BugTrackingRequest detail = new BugTrackingRequest();

        BeanUtils.copyProperties(entity,detail);

        return detail;
    }

    public BugTrackingRequest update(BugTrackingRequest detail){
        BugTracking entity = bugTrackingRepository.findById(detail.getBugId()).orElse(null);
        assertNotNull(entity,
                "No bug found by this id " + detail.getBugId());
        detail.setBugId(null);
        detail.setLastupdate(new Timestamp(System.currentTimeMillis()));
        BeanUtils.copyProperties(detail,entity,getNullPropertyNames(detail));
        entity=bugTrackingRepository.saveAndFlush(entity);
        BeanUtils.copyProperties(entity,detail);

        return detail;
    }

    public BugTrackingListRequest findBugsByCreationDate(Timestamp creationdate1, Timestamp creationdate2){

        List<BugTracking> listOfBugs = bugTrackingRepository.findAllBetweenDates(creationdate1, creationdate2);

        BugTrackingListRequest bugTrackingListRequest = new BugTrackingListRequest();
        for (BugTracking bug : listOfBugs) {
            BugTrackingRequest detail = new BugTrackingRequest();

            BeanUtils.copyProperties(bug,detail);
            bugTrackingListRequest.addBugTrackingRequest(detail);
        }

        return bugTrackingListRequest;
    }

    public BugTrackingListRequest findBugsByCreationDateWithStatus(Timestamp creationdate1, Timestamp creationdate2, Boolean status){

        List<BugTracking>  listOfBugs = bugTrackingRepository.findAllByStatusBetweenDates(status, creationdate1, creationdate2);


        BugTrackingListRequest bugTrackingListRequest = new BugTrackingListRequest();
        for (BugTracking bug : listOfBugs) {
            BugTrackingRequest detail = new BugTrackingRequest();

            BeanUtils.copyProperties(bug,detail);
            bugTrackingListRequest.addBugTrackingRequest(detail);
        }

        return bugTrackingListRequest;
    }

    public void delete(Integer id) {

        BugTracking entity = bugTrackingRepository.findById(id).orElse(null);
        assertNotNull(entity,
                "No bug found by id " + id);


        bugTrackingRepository.delete(entity);
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
