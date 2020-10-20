package com.innopolis.innometrics.restapi.service;

import com.innopolis.innometrics.restapi.entitiy.CollectorVersion;
import com.innopolis.innometrics.restapi.repository.CollectorVersionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CollectorVersionService {
    private static Logger LOG = LogManager.getLogger();

    @Autowired
    CollectorVersionRepository versionRepository;

    public String getCurrentVersion(String osversion){
        CollectorVersion myVersion = versionRepository.findByOsversion(osversion);

        if (myVersion != null)
            return myVersion.getValue();
        return "";
    }

    public boolean updateCurrentVersion(String osversion, String newVersion){
        CollectorVersion myVersion = versionRepository.findByOsversion(osversion);
        myVersion.setValue(newVersion);
        versionRepository.save(myVersion);

        return true;
    }
}
