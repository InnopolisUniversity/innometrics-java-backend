package com.innopolis.innometrics.authserver.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserListResponse implements Serializable {
    List<UserResponse> userList;

    public UserListResponse() {
        userList = new ArrayList<>();
    }

    public UserListResponse(List<UserResponse> userList) {
        this.userList = userList;
    }

    public List<UserResponse> getUserList() {
        return userList;
    }

    public void setUserList(List<UserResponse> userList) {
        this.userList = userList;
    }

}
