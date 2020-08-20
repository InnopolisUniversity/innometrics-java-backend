package com.innopolis.innometrics.authserver.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RoleListResponse  implements Serializable {
    List<RoleResponse> roleList;

    public RoleListResponse() {
        roleList = new ArrayList<>();
    }

    public RoleListResponse(List<RoleResponse> roleList) {
        this.roleList = roleList;
    }

    public List<RoleResponse> getRoleList() {
        return roleList;
    }

    public void setUserList(List<RoleResponse> roleList) {
        this.roleList = roleList;
    }

    public void addRoleResponse(RoleResponse roleResponse){
        this.roleList.add(roleResponse);
    }
}
