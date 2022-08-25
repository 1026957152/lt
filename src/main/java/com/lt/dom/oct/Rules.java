package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumAssetType;
import com.lt.dom.otcenum.EnumRuleAttributes;
import com.lt.dom.otcenum.EnumRuleConditionType;
import com.lt.dom.otcenum.EnumRuleOperator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Rules {

    @Version
    private Integer version;
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Id
    private long id;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    @NotNull
    private EnumRuleConditionType conditionType;
    @NotNull
    private EnumRuleAttributes attribute;
    private EnumRuleOperator operator;


    private String string_value;
    private Long numeric_value;
    private Boolean boolean_value;

    public EnumRuleConditionType getConditionType() {
        return conditionType;
    }

    public void setConditionType(EnumRuleConditionType conditionType) {
        this.conditionType = conditionType;
    }

    public EnumRuleAttributes getAttribute() {
        return attribute;
    }

    public void setAttribute(EnumRuleAttributes attribute) {
        this.attribute = attribute;
    }

    public EnumRuleOperator getOperator() {
        return operator;
    }

    public void setOperator(EnumRuleOperator operator) {
        this.operator = operator;
    }

    public String getString_value() {
        return string_value;
    }

    public void setString_value(String string_value) {
        this.string_value = string_value;
    }

    public Long getNumeric_value() {
        return numeric_value;
    }

    public void setNumeric_value(Long numeric_value) {
        this.numeric_value = numeric_value;
    }

    public Boolean getBoolean_value() {
        return boolean_value;
    }

    public void setBoolean_value(Boolean boolean_value) {
        this.boolean_value = boolean_value;
    }
}
