package com.lt.dom.vo;

import com.lt.dom.otcenum.EnumRequestApprove;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.hateoas.Link;

public class DeviceScanValidatorVo {

    private String id;
    private boolean pass;




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPass(boolean pass) {
        this.pass = pass;
    }

    public boolean getPass() {
        return pass;
    }


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
