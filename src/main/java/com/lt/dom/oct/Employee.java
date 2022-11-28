package com.lt.dom.oct;


import com.lt.dom.otcenum.EnumEmployeeAccessLevel;
import com.lt.dom.otcenum.EnumEmployeeStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Employee {


    private LocalDateTime created_at;

    private EnumEmployeeStatus status;

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

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

    private Long userId;
    private Long suplierId;

    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String note;
    
//##@Column(unique=true) 
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
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

    public EnumEmployeeStatus getStatus() {
        return status;
    }

    public void setStatus(EnumEmployeeStatus status) {
        this.status = status;
    }
}
