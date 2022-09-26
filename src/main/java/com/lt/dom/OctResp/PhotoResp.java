package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Column;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PhotoResp {



    private String url_thumbnail;
    private String url;
    private String url_large;
    private String url_xlarge;
    private String url_original;
    private String desc;
    
//##@Column(unique=true)
private String code;
    private boolean visiable;


    public void setUrl_thumbnail(String url_thumbnail) {
        this.url_thumbnail = url_thumbnail;
    }

    public String getUrl_thumbnail() {
        return url_thumbnail;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl_large(String url_large) {
        this.url_large = url_large;
    }

    public String getUrl_large() {
        return url_large;
    }

    public void setUrl_xlarge(String url_xlarge) {
        this.url_xlarge = url_xlarge;
    }

    public String getUrl_xlarge() {
        return url_xlarge;
    }

    public void setUrl_original(String url_original) {
        this.url_original = url_original;
    }

    public String getUrl_original() {
        return url_original;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setVisiable(boolean visiable) {
        this.visiable = visiable;
    }

    public boolean getVisiable() {
        return visiable;
    }
}
