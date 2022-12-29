package com.lt.dom.vo;

import com.lt.dom.oct.Device;
import com.lt.dom.oct.User;
import com.lt.dom.otcenum.EnumDeviceStatus;
import com.lt.dom.otcenum.EnumDeviceType;
import com.lt.dom.otcenum.EnumValidatorType;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class ValidatedByTypeVo {


    @NotNull
    private EnumDeviceType type;
    @NotNull
    private EnumValidatorType validatorType;
    private Device device;
    private User user;

    public EnumDeviceType getType() {
        return type;
    }

    public void setType(EnumDeviceType type) {
        this.type = type;
    }

    public EnumValidatorType getValidatorType() {
        return validatorType;
    }

    public void setValidatorType(EnumValidatorType validatorType) {
        this.validatorType = validatorType;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long funGetValidator() {

        if(validatorType.equals(EnumValidatorType.特定的人员)){
            return user.getId();
        }
        if(validatorType.equals(EnumValidatorType.特定机器)){
            return device.getId();
        }

        return null;
    }
    public String funGetValidatorCode() {

        if(validatorType.equals(EnumValidatorType.特定的人员)){
            return user.getCode();
        }
        if(validatorType.equals(EnumValidatorType.特定机器)){
            return device.getCode();
        }

        return null;
    }
}
