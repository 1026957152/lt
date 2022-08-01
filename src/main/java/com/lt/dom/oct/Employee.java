package com.lt.dom.oct;


import com.lt.dom.otcenum.EnumEmployeeAccessLevel;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Employee {

    @Id
    private long id;

    private long userId;
    private long suplierId;

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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getSuplierId() {
        return suplierId;
    }

    public void setSuplierId(long suplierId) {
        this.suplierId = suplierId;
    }
}
