package com.lt.dom.OctResp;

import com.lt.dom.oct.Base;
import com.lt.dom.oct.ComponentRight;
import com.lt.dom.oct.ValidatorGroup;
import com.lt.dom.otcenum.EnumValidatorType;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.List;
import java.util.stream.Collectors;


public class ValidatorResp  {  // 这个就是机器了啊

    private Long user;

    private Long device;
    private Long role;
    private Long id;

    public static List<EnumLongIdResp> EnumList(List<ValidatorResp> componentRightList) {
        return componentRightList.stream().map(x->{

            EnumLongIdResp enumResp = new EnumLongIdResp();
            enumResp.setId(x.getId());
            //  enumResp.setName(x.name());
            enumResp.setText(x.getComponentRight().getName()+"_"+x.getComponentRight().getCode());
            return enumResp;
        }).collect(Collectors.toList());
    }

    public Long getDevice() {
        return device;
    }

    public void setDevice(Long equipmentId) {
        this.device = equipmentId;
    }

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



    ComponentRightResp componentRight;


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

    public void setComponentRight(ComponentRightResp componentRight) {
        this.componentRight = componentRight;
    }

    public ComponentRightResp getComponentRight() {
        return componentRight;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
