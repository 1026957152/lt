package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumAssetType;
import com.lt.dom.otcenum.EnumValueType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Setting {
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

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EnumValueType getValue_type() {
        return value_type;
    }

    public void setValue_type(EnumValueType value_type) {
        this.value_type = value_type;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getInt_value() {
        return int_value;
    }

    public void setInt_value(Integer int_value) {
        this.int_value = int_value;
    }

    public String getString_value() {
        return string_value;
    }

    public void setString_value(String string_value) {
        this.string_value = string_value;
    }

    public Float getFloat_value() {
        return float_value;
    }

    public void setFloat_value(Float float_value) {
        this.float_value = float_value;
    }

    public Boolean getBoolean_value() {
        return boolean_value;
    }

    public void setBoolean_value(Boolean boolean_value) {
        this.boolean_value = boolean_value;
    }

    private EnumValueType value_type;
    private Integer int_value;
    private String string_value;
    private Float float_value;

    private Boolean boolean_value;

}
