package com.lt.dom.otcReq;

import com.lt.dom.otcenum.EnumValueType;

import javax.validation.constraints.Size;
import java.util.List;

public class PrefereneVo {


    private String first_name;

    private String last_name;
    private String name;

    private String phone;
    private String id;
    private Boolean boolean_value;
    private Integer int_value;
    private Float float_value;
    private String string_value;
    private EnumValueType value_type;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Size(min = 1)
    private List<String> roles;

    private String password;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getBoolean_value() {
        return boolean_value;
    }

    public void setBoolean_value(Boolean boolean_value) {
        this.boolean_value = boolean_value;
    }

    public Integer getInt_value() {
        return int_value;
    }

    public void setInt_value(Integer int_value) {
        this.int_value = int_value;
    }

    public Float getFloat_value() {
        return float_value;
    }

    public void setFloat_value(Float float_value) {
        this.float_value = float_value;
    }

    public String getString_value() {
        return string_value;
    }

    public void setString_value(String string_value) {
        this.string_value = string_value;
    }

    public void setValue_type(EnumValueType value_type) {
        this.value_type = value_type;
    }

    public EnumValueType getValue_type() {
        return value_type;
    }
}
