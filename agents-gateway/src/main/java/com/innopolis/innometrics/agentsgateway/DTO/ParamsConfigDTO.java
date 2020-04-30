package com.innopolis.innometrics.agentsgateway.DTO;


import java.io.Serializable;

public class ParamsConfigDTO implements Serializable {

    private String paramname;
    private String paramtype;
    private String value;

    public ParamsConfigDTO() {
    }

    public String getParamname() {
        return paramname;
    }

    public void setParamname(String paramname) {
        this.paramname = paramname;
    }

    public String getParamtype() {
        return paramtype;
    }

    public void setParamtype(String paramtype) {
        this.paramtype = paramtype;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
