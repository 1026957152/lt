package com.lt.dom.otcReq;

import com.lt.dom.otcenum.EnumValidatorRedemExtent;
import com.lt.dom.otcenum.EnumValidatorType;

import java.util.List;


public class ValidatorPojo {  // 这个就是机器了啊


    private long quipment;
    private long user;

    public long getQuipment() {
        return quipment;
    }

    public void setQuipment(long quipment) {
        this.quipment = quipment;
    }

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    private EnumValidatorType type; //指定人工，机器, 所有人工

    public EnumValidatorType getType() {
        return type;
    }

    public void setType(EnumValidatorType type) {
        this.type = type;
    }


    private List<EnumValidatorRedemExtent> extents; //指定人工，机器, 所有人工

    public List<EnumValidatorRedemExtent> getExtents() {
        return extents;
    }

    public void setExtents(List<EnumValidatorRedemExtent> extents) {
        this.extents = extents;
    }
}
