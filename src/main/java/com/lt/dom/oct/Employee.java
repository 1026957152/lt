package com.lt.dom.oct;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Employee {

    @Id
    private long id;

    private String userId;
    private String suplierId;

    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String note;



    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Transient
    private EnumEmployeeAccessLevel enumEmployeeAccessLevel;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSuplierId() {
        return suplierId;
    }

    public void setSuplierId(String suplierId) {
        this.suplierId = suplierId;
    }
}
