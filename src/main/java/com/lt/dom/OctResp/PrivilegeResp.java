package com.lt.dom.OctResp;

import com.lt.dom.oct.Role;

import javax.persistence.*;
import java.util.Collection;


public class PrivilegeResp {


    private String name;

    private Collection<Role> roles;


    public PrivilegeResp(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}