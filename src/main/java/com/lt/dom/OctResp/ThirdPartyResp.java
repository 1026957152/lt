package com.lt.dom.OctResp;


import com.lt.dom.oct.Region;
import com.lt.dom.oct.ThirdParty;
import com.lt.dom.otcenum.EnumThirdParty;
import com.lt.dom.otcenum.EnumThirdPartyStatus;
import org.springframework.hateoas.EntityModel;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

public class ThirdPartyResp extends BaseResp{



    private String name;
    private PhotoResp photo;
    private String code;

    private EnumThirdParty platform;
    private String description;
    private List products;




    private EnumThirdPartyStatus status;


    private String baseURL;

    private String partner_id;
    private String authorization_code;
    private String platform_text;
    private String status_text;


    public static ThirdPartyResp simpleFrom(ThirdParty region) {
        ThirdPartyResp resp = new ThirdPartyResp();
        resp.setName(region.getName());
        resp.setCode(region.getCode());
        resp.setPlatform(region.getPlatform());
        resp.setDescription(region.getDescription());
        return resp;

    }
    public static ThirdPartyResp from(ThirdParty region) {
        ThirdPartyResp resp = new ThirdPartyResp();
        resp.setName(region.getName());
        resp.setCode(region.getCode());
        resp.setPlatform(region.getPlatform());
        resp.setPlatform_text(region.getPlatform().toString());
        resp.setDescription(region.getDescription());
        resp.setStatus(region.getStatus());
        resp.setStatus_text(region.getStatus().toString());
        resp.setBaseURL(region.getBaseURL());
        resp.setPartner_id(region.getPartner_id());
        resp.setAuthorization_code(region.getAuthorization_code());

        resp.setCreatedDate(region.getCreatedDate());
        resp.setModifiedDate(region.getModifiedDate());

        return resp;

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

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public EnumThirdParty getPlatform() {
        return platform;
    }

    public void setPlatform(EnumThirdParty platform) {
        this.platform = platform;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public <R> void setProducts(List products) {
        this.products = products;
    }

    public List getProducts() {
        return products;
    }

    public EnumThirdPartyStatus getStatus() {
        return status;
    }

    public void setStatus(EnumThirdPartyStatus status) {
        this.status = status;
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

    public void setPlatform_text(String platform_text) {
        this.platform_text = platform_text;
    }

    public String getPlatform_text() {
        return platform_text;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public String getStatus_text() {
        return status_text;
    }
}
