package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumValidatorRedemExtent;

import javax.persistence.*;
import java.util.List;


@Entity
public class AccessValidator {
    @Version
    private Integer version;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id;


    @Transient
    List<Validator_> validators; //
    private long validatorId;


    private long ralativeId;
    private int supplierId;

    public long getRalativeId() {
        return ralativeId;
    }

    public void setRalativeId(long ralativeId) {
        this.ralativeId = ralativeId;
    }

    @Transient
    ComponentRight componentRight;

    private EnumValidatorRedemExtent extend; //闸机, 车牌识别摄像头

    public long getValidatorId() {
        return validatorId;
    }

    public void setValidatorId(long validatorId) {
        this.validatorId = validatorId;
    }

    public EnumValidatorRedemExtent getExtend() {
        return extend;
    }

    public void setExtend(EnumValidatorRedemExtent extend) {
        this.extend = extend;
    }

    public List<Validator_> getValidators() {
        return validators;
    }

    public void setValidators(List<Validator_> validators) {
        this.validators = validators;
    }

    @Transient
    private ValidatorGroup validatorGroup;



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
