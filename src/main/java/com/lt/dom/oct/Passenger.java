package com.lt.dom.oct;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class Passenger {
    @Version
    private Integer version;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    private long user;
    private String code;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }




    @NotEmpty
    private String name;///
    private String age;///

    @NotEmpty
    private String idNo;///

    @NotEmpty
    private String tel_home;///



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }


    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getTel_home() {
        return tel_home;
    }

    public void setTel_home(String tel_home) {
        this.tel_home = tel_home;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public long getUser() {
        return user;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
