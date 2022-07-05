package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;


@Entity
public class AccessValidator {


    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @JsonProperty("id")
    private long id;

    @Transient
    ComponentRight componentRight;


    private String type; //指定人工，机器, 所有人工

    @Transient
    private List<User> userList; // 人工的话

    @Transient
    private ValidatorGroup validatorGroup;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ValidatorGroup getValidatorGroup() {
        return validatorGroup;
    }

    public void setValidatorGroup(ValidatorGroup validatorGroup) {
        this.validatorGroup = validatorGroup;
    }

    public ComponentRight getComponentRight() {
        return componentRight;
    }

    public void setComponentRight(ComponentRight componentRight) {
        this.componentRight = componentRight;
    }
}
