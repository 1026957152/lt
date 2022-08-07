package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.domain.SettleAccount;
import com.lt.dom.oct.Asset;
import com.lt.dom.oct.Supplier;
import com.lt.dom.oct.User;
import com.lt.dom.otcenum.EnumBussinessType;
import com.lt.dom.otcenum.EnumSupplierType;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResp extends RepresentationModel<UserResp> {

    private  boolean hired;
    private String first_name;//	string	The user’s first name

    private String last_name;//	string	The user’s last name
    private String username;//	string	The user’s username*
    private String email;//	string	The user’s email address

    private String phone;//	string	The user’s phone number
    private String code;
    private EnumSupplierType supplier_type;
    private String supplier_name;
    private String supplier_desc;
    private EnumBussinessType supplier_bussiness_type;
    private String supplier_code;
    private EntityModel supplier;
    private String realName;

    public static UserResp from(User user, Supplier supplier) {
        UserResp userResp = new UserResp();
        userResp.setPhone(user.getPhone());
        userResp.setFirst_name(user.getFirst_name());
        userResp.setLast_name(user.getLast_name());
        userResp.setCode(user.getCode());
        userResp.setSupplier_name(supplier.getCode());
        userResp.setSupplier_desc(supplier.getDesc());
        userResp.setSupplier_bussiness_type(supplier.getBusiness_type());
        userResp.setSupplier_type(supplier.getType());
        userResp.setSupplier_code(supplier.getCode());
        userResp.setRoles(user.getRoles().stream().map(x->{
            return RoleResp.from(x);
        }).collect(Collectors.toList()));
        return userResp;
    }

    public  void setHired(boolean hired) {
        this.hired = hired;
    }

    public  boolean getHired() {
        return hired;
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static UserResp from(User user) {
        UserResp userResp = new UserResp();
        userResp.setPhone(user.getPhone());
        userResp.setFirst_name(user.getFirst_name());
        userResp.setRealName(user.getRealName());
        userResp.setLast_name(user.getLast_name());
        userResp.setCode(user.getCode());
        userResp.setRoles(user.getRoles().stream().map(x->{
            return RoleResp.from(x);
        }).collect(Collectors.toList()));
        return userResp;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }


    private List<RoleResp> roles;

    public List<RoleResp> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleResp> roles) {
        this.roles = roles;
    }

    public void setSupplier_type(EnumSupplierType supplier_type) {
        this.supplier_type = supplier_type;
    }

    public EnumSupplierType getSupplier_type() {
        return supplier_type;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_desc(String supplier_desc) {
        this.supplier_desc = supplier_desc;
    }

    public String getSupplier_desc() {
        return supplier_desc;
    }

    public void setSupplier_bussiness_type(EnumBussinessType supplier_bussiness_type) {
        this.supplier_bussiness_type = supplier_bussiness_type;
    }

    public EnumBussinessType getSupplier_bussiness_type() {
        return supplier_bussiness_type;
    }


    public String getSupplier_code() {
        return supplier_code;
    }

    public void setSupplier_code(String supplier_code) {
        this.supplier_code = supplier_code;
    }

    public void setSupplier(EntityModel supplier) {
        this.supplier = supplier;
    }

    public EntityModel getSupplier() {
        return supplier;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRealName() {
        return realName;
    }
}
