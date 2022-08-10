package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.otcenum.EnumValidatorType;

import javax.persistence.*;

@Entity
public class Validator {  // 这个就是机器了啊

    @Version
    private Integer version;
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @JsonProperty("id")
    private long id;
    private long userId;

    private long equipmentId;

    public long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(long equipmentId) {
        this.equipmentId = equipmentId;
    }

    private EnumValidatorType type; //指定人工，机器, 所有人工

    public EnumValidatorType getType() {
        return type;
    }

    public void setType(EnumValidatorType type) {
        this.type = type;
    }

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
