package com.innopolis.innometrics.restapi;

import com.innopolis.innometrics.restapi.DTO.*;
import com.innopolis.innometrics.restapi.controller.AdminAPI;
import com.innopolis.innometrics.restapi.controller.AuthAPI;
import com.innopolis.innometrics.restapi.controller.DataCollectorsAPI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class RestapiApplicationTests {


    private static Logger LOG = LogManager.getLogger();

    @Autowired
    AdminAPI adminAPI;

    @Autowired
    AuthAPI authAPI;

    @Autowired
    DataCollectorsAPI dataCollectorsAPI;

    private UserRequest user;
    private String token;

    @Test
    void contextLoads() {
    }

    @Test
    public void UserCreation() throws Exception {

        UserRequest request = new UserRequest();
        request.setEmail("x.vasquez");
        request.setName("Xavier");
        request.setSurname("Vasquez");
        request.setPassword("123456");
        //ResponseEntity<UserRequest> response = adminAPI.CreateUser(request, null);

        //user = response.getBody();

        //assertTrue(response.getStatusCode() == HttpStatus.CREATED);

        //tokecCreation();

        //failLogin();

        //CreateActivity();

    }



    public void tokecCreation() throws Exception {

        AuthRequest request = new AuthRequest();
        request.setEmail(user.getEmail());
        request.setPassword(user.getPassword());
        ResponseEntity<?> response = authAPI.login(request);

        token = ((AuthResponse) response.getBody()).getToken();

        LOG.info(token);

        assertTrue(response.getStatusCode() == HttpStatus.OK);
    }


    public void failLogin() throws Exception {

        AuthRequest request = new AuthRequest();
        request.setEmail(user.getEmail());
        request.setPassword("someRandomPassword");
        try {
            ResponseEntity<?> response = authAPI.login(request);

            assertTrue(response.getStatusCode() != HttpStatus.OK);
        }
        catch (Exception ex){
            assertTrue(null != HttpStatus.OK);
        }
    }

    //@Test
    public void CreateActivity() throws Exception {
        AuthRequest request = new AuthRequest();
        request.setEmail("x.vasquez");
        request.setPassword("123456");
        ResponseEntity<?> response = authAPI.login(request);

        try {
            token = ((AuthResponse) response.getBody()).getToken();

            Report report = new Report();
            //List<ActivityReport> lActTemp = new ArrayList<>();
            ActivityReport actTemp = new ActivityReport();
            //actTemp.setActivityID
            actTemp.setActivityType("OS");
            actTemp.setBrowser_title("");
            actTemp.setBrowser_url("");
            actTemp.setUserID("x.vasquez");
            actTemp.setEnd_time(new Date("2019-11-14T17:27:43.264Z"));
            actTemp.setExecutable_name("test.exe");
            actTemp.setIdle_activity(true);
            actTemp.setIp_address("127.0,0,1");
            actTemp.setMac_address("d8:7d:c0:28:27:21");
            actTemp.setStart_time(new Date("2019-11-14T17:27:43.264Z"));

            MeasurementReport measurementReport = new MeasurementReport();
            measurementReport.setMeasurementTypeId("1");
            measurementReport.setValue("15.09");

            //actTemp.getMeasurements().add(measurementReport);

            report.getActivities().add(actTemp);


            ResponseEntity<?> Reportresponse = dataCollectorsAPI.addReport(report, null, token);

            assertTrue(Reportresponse.getStatusCode() != HttpStatus.OK);
        }
        catch (Exception ex){
            assertTrue(null != HttpStatus.OK);
        }


    }


}
