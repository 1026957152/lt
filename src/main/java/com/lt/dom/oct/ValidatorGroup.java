package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;


@Entity
public class ValidatorGroup {

    @Version
    private Integer version;
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @JsonProperty("id")
    private long id;




    @Transient
    AccessValidator accessValidator ;




    @Transient
    private ComponentRight componentRight;

    private long componentRightId ;

    public long getComponentRightId() {
        return componentRightId;
    }

    public void setComponentRightId(long componentRightId) {
        this.componentRightId = componentRightId;
    }

    private long accessValidatorId ;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAccessValidatorId() {
        return accessValidatorId;
    }

    public void setAccessValidatorId(long accessValidatorId) {
        this.accessValidatorId = accessValidatorId;
    }




    public AccessValidator getAccessValidator() {
        return accessValidator;
    }

    public void setAccessValidator(AccessValidator accessValidator) {
        this.accessValidator = accessValidator;
    }
}
