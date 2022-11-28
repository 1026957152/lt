package com.lt.dom.vo;


import com.lt.dom.oct.User;
import com.lt.dom.otcenum.EnumIdType;
import com.lt.dom.otcenum.EnumPlatform;

import javax.validation.constraints.NotEmpty;


public class PlatUserVo {


    @NotEmpty
    private String name;///

    @NotEmpty
    private String id;
    @NotEmpty
    private String tel_home;
    private EnumPlatform platform;
    private UserVo userVo;
    private String phone;
    private EnumIdType id_type;
    private String id_number;
    private Boolean paid;
    private Float paidAmount;
    private User user;
    private String tracking_id;
    private String remark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTel_home() {
        return tel_home;
    }

    public void setTel_home(String tel_home) {
        this.tel_home = tel_home;
    }

    public void setPlatform(EnumPlatform platform) {
        this.platform = platform;
    }

    public EnumPlatform getPlatform() {
        return platform;
    }

    public void setUserVo(UserVo userVo) {
        this.userVo = userVo;
    }

    public UserVo getUserVo() {
        return userVo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public EnumIdType getId_type() {
        return id_type;
    }

    public void setId_type(EnumIdType id_type) {
        this.id_type = id_type;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public Boolean isPaid() {
        return paid;
    }

    public void setPaidAmount(Float paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Float getPaidAmount() {
        return paidAmount;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setTracking_id(String tracking_id) {
        this.tracking_id = tracking_id;
    }

    public String getTracking_id() {
        return tracking_id;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }
}
