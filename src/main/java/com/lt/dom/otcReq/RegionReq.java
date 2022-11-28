package com.lt.dom.otcReq;

import com.lt.dom.OctResp.MediaResp;
import com.lt.dom.OctResp.PhotoResp;
import com.lt.dom.oct.Base;
import com.lt.dom.oct.Tag;
import com.lt.dom.otcenum.EnumThirdParty;
import com.lt.dom.otcenum.EnumThirdPartyStatus;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;


public class RegionReq {



    private String name;
    private PhotoResp photo;


    private MediaResp.Description description;



    @Enumerated(EnumType.STRING)
    private EnumThirdParty platform;
    @Enumerated(EnumType.STRING)
    private EnumThirdPartyStatus status;
    private String code;
    private String baseURL;

    private String partner_id;
    private String authorization_code;


    public EnumThirdParty getPlatform() {
        return platform;
    }

    public void setPlatform(EnumThirdParty platform) {
        this.platform = platform;
    }

    public EnumThirdPartyStatus getStatus() {
        return status;
    }

    public void setStatus(EnumThirdPartyStatus status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public String getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(String partner_id) {
        this.partner_id = partner_id;
    }

    public String getAuthorization_code() {
        return authorization_code;
    }

    public void setAuthorization_code(String authorization_code) {
        this.authorization_code = authorization_code;
    }

    public MediaResp.Description getDescription() {
        return description;
    }

    public void setDescription(MediaResp.Description description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PhotoResp getPhoto() {
        return photo;
    }

    public void setPhoto(PhotoResp photo) {
        this.photo = photo;
    }
}
