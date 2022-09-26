package com.lt.dom.OctResp;

import com.lt.dom.otcenum.EnumBussinessType;
import com.lt.dom.otcenum.EnumSupplierType;
import org.springframework.hateoas.EntityModel;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.UUID;


public class AuthsResp {




    private String phone;

    private String token;
    private String real_name;
    private String first_name;
    private String last_name;

    private EntityModel<SupplierResp> supplier;

    private EntityModel<UserResp> profile;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getLast_name() {
        return last_name;
    }



    public void setSupplier(EntityModel<SupplierResp> supplier) {
        this.supplier = supplier;
    }

    public EntityModel getSupplier() {
        return supplier;
    }


    public void setProfile(EntityModel<UserResp> profile) {
        this.profile = profile;
    }

    public EntityModel<UserResp> getProfile() {
        return profile;
    }



/*
    
//##@Column(unique=true) 
private String code;
    private EnumSupplierType supplier_type;
    private String supplier_name;
    private String supplier_desc;
    private EnumBussinessType supplier_bussiness_type;
    private String supplier_code;
    private List<RoleResp> roles;

    public List<RoleResp> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleResp> roles) {
        this.roles = roles;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public EnumSupplierType getSupplier_type() {
        return supplier_type;
    }

    public void setSupplier_type(EnumSupplierType supplier_type) {
        this.supplier_type = supplier_type;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    public String getSupplier_desc() {
        return supplier_desc;
    }

    public void setSupplier_desc(String supplier_desc) {
        this.supplier_desc = supplier_desc;
    }

    public EnumBussinessType getSupplier_bussiness_type() {
        return supplier_bussiness_type;
    }

    public void setSupplier_bussiness_type(EnumBussinessType supplier_bussiness_type) {
        this.supplier_bussiness_type = supplier_bussiness_type;
    }

    public String getSupplier_code() {
        return supplier_code;
    }

    public void setSupplier_code(String supplier_code) {
        this.supplier_code = supplier_code;
    }

    public void setInfo(UserResp info) {
        this.info = info;
    }

    public UserResp getInfo() {
        return info;
    }*/
}
