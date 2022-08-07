package com.lt.dom.oct;


import com.lt.dom.otcenum.EnumEmployeeAccessLevel;

import javax.persistence.*;

@Entity
public class Employee {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private long userId;
    private long suplierId;

    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String note;
    private String code;


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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
