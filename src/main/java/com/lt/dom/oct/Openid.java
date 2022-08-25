package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumAssetType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Openid {


    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Id
    private long id;
    private long userId;
    private boolean link;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private EnumAssetType type;

    @NotNull
    private String code;//	string	The ID of the user
    @NotNull
    private String openid;
    @NotEmpty
    private String openid_name;
    private Integer openid_gender;
    private String openid_image;

    public EnumAssetType getType() {
        return type;
    }

    public void setType(EnumAssetType type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getOpenid_name() {
        return openid_name;
    }

    public void setOpenid_name(String openid_name) {
        this.openid_name = openid_name;
    }

    public Integer getOpenid_gender() {
        return openid_gender;
    }

    public void setOpenid_gender(Integer openid_gender) {
        this.openid_gender = openid_gender;
    }

    public String getOpenid_image() {
        return openid_image;
    }

    public void setOpenid_image(String openid_image) {
        this.openid_image = openid_image;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public void setLink(boolean link) {
        this.link = link;
    }

    public boolean getLink() {
        return link;
    }
}
