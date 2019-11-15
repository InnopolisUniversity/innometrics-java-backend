package com.innopolis.innometrics.restapi;

import com.innopolis.innometrics.restapi.DTO.UserRequest;
import com.innopolis.innometrics.restapi.controller.AdminAPI;
import com.innopolis.innometrics.restapi.controller.AuthAPI;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class RestapiApplicationTests {

    @Autowired
    AdminAPI adminAPI;

    @Autowired
    AuthAPI authAPI;

    @Test
    void contextLoads() {
    }

    @Test
    public void UserCreation() {

        UserRequest request = new UserRequest();
        request.setEmail("x.vasquez");
        request.setName("Xavier");
        request.setSurname("Vasquez");
        request.setPassword("123456");

        HttpStatus status = adminAPI.CreateUser(request, null).getStatusCode();

        assertTrue(status == HttpStatus.CREATED);
    }

}
