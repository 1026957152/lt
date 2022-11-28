package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.otcenum.EnumValidatorType;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.*;

@Entity
public class Validator_  extends Base{  // 这个就是机器了啊

    private Long user;

    private Long device;
    private Long role;
    private Boolean active;

    public Long getDevice() {
        return device;
    }

    public void setDevice(Long equipmentId) {
        this.device = equipmentId;
    }
    @Enumerated(EnumType.STRING)
    private EnumValidatorType type; //指定人工，机器, 所有人工

    public EnumValidatorType getType() {
        return type;
    }

    public void setType(EnumValidatorType type) {
        this.type = type;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long userId) {
        this.user = userId;
    }
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Transient
    private ValidatorGroup validatorGroup;
    private long validatorGroupId;


    @Transient
    ComponentRight componentRight;
    private long componentRightId;

    public long getComponentRightId() {
        return componentRightId;
    }

    public void setComponentRightId(long componentRightId) {
        this.componentRightId = componentRightId;
    }


    public Long getValidatorGroupId() {
        return validatorGroupId;
    }

    public void setValidatorGroupId(Long validatorGroupId) {
        this.validatorGroupId = validatorGroupId;
    }

    public ValidatorGroup getValidatorGroup() {
        return validatorGroup;
    }

    public void setValidatorGroup(ValidatorGroup validatorGroup) {
        this.validatorGroup = validatorGroup;
    }
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public Long getRole() {
        return role;
    }

    public void setRole(Long role) {
        this.role = role;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean isActive() {
        return active;
    }
}
