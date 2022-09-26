package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.otcenum.EnumValidatorType;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.*;

@Entity
public class Validator_ {  // 这个就是机器了啊

    @Version
    private Integer version;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @JsonProperty("id")
    private long id;
    private Long user;

    private long device;
    private long role;

    public long getDevice() {
        return device;
    }

    public void setDevice(long equipmentId) {
        this.device = equipmentId;
    }

    private EnumValidatorType type; //指定人工，机器, 所有人工

    public EnumValidatorType getType() {
        return type;
    }

    public void setType(EnumValidatorType type) {
        this.type = type;
    }

    public long getUser() {
        return user;
    }

    public void setUser(long userId) {
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getValidatorGroupId() {
        return validatorGroupId;
    }

    public void setValidatorGroupId(long validatorGroupId) {
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

    public long getRole() {
        return role;
    }

    public void setRole(long role) {
        this.role = role;
    }
}
