package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;


@Entity
public class ComponentRight {   // 这个是 下单的时候， 从 product 中生成 的

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @JsonProperty("id")
    private long id;

    public long getId() {
        return id;
    }



    public void setId(long id) {
        this.id = id;
    }

    private String name;
    private String note;

    @Transient
    private AccessValidator accessValidator ;
    private long accessValidatorId;

    public long getAccessValidatorId() {
        return accessValidatorId;
    }

    public void setAccessValidatorId(long accessValidatorId) {
        this.accessValidatorId = accessValidatorId;
    }

    @Transient
    List<ComponentRightVounch> componentRightVounches;


    public List<ComponentRightVounch> getComponentRightVounches() {
        return componentRightVounches;
    }

    public void setComponentRightVounches(List<ComponentRightVounch> componentRightVounches) {
        this.componentRightVounches = componentRightVounches;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public AccessValidator getAccessValidator() {
        return accessValidator;
    }

    public void setAccessValidator(AccessValidator accessValidator) {
        this.accessValidator = accessValidator;
    }
}
