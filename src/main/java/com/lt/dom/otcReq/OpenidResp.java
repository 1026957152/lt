package com.lt.dom.otcReq;

import com.lt.dom.oct.Openid;
import org.springframework.hateoas.RepresentationModel;

import static java.util.Objects.nonNull;


public class OpenidResp extends RepresentationModel<OpenidResp> {


    private String openid;
    private String code;
    private String name;
    private Integer gender;
    private String image;
    private String token;
    private boolean real_name;
    private boolean link;

    public static OpenidResp from(Openid openid1) {
        OpenidResp openidResp = new OpenidResp();
        //openidResp.setOpenid(openid1.getOpenid());
        openidResp.setCode(openid1.getCode());
        openidResp.setName(openid1.getOpenid_name());
        openidResp.setGender(openid1.getOpenid_gender());
        openidResp.setImage(openid1.getOpenid_image());
        openidResp.setLink(openid1.getLink());
        openidResp.setReal_name(nonNull(openid1.getUserId()));

        return openidResp;
    }

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
}
