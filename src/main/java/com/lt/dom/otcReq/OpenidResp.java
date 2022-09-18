package com.lt.dom.otcReq;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.Openid;
import com.lt.dom.oct.User;
import org.javatuples.Pair;
import org.springframework.hateoas.RepresentationModel;

import static java.util.Objects.nonNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OpenidResp extends RepresentationModel<OpenidResp> {


    private String openid;
    

private String code;
    private String name;
    private Integer gender;
    private String image;
    private String token;
    private boolean real_name;
    private boolean link;
    private String full_name;
    private String id_card;



    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getGender() {
        return gender;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setReal_name(boolean real_name) {
        this.real_name = real_name;
    }

    public boolean getReal_name() {
        return real_name;
    }

    public void setLink(boolean link) {
        this.link = link;
    }

    public boolean getLink() {
        return link;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getId_card() {
        return id_card;
    }
}
