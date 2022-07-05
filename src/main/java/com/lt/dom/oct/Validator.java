package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class Validator {  // 这个就是机器了啊
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @JsonProperty("id")
    private long id;
    private long userId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String type; //闸机, 车牌识别摄像头

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
}
