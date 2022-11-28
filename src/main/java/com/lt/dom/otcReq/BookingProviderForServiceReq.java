package com.lt.dom.otcReq;

import com.lt.dom.oct.Base;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;


//https://developer.makeplans.com/#slots

@Entity
public class BookingProviderForServiceReq extends Base {


    @NotNull
    private Long resource;
    private String title;
    private String slug;
    private String desc_long ;
    private String desc_short;
    private String privacyLevel ;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDesc_long() {
        return desc_long;
    }

    public void setDesc_long(String desc_long) {
        this.desc_long = desc_long;
    }

    public String getDesc_short() {
        return desc_short;
    }

    public void setDesc_short(String desc_short) {
        this.desc_short = desc_short;
    }

    public String getPrivacyLevel() {
        return privacyLevel;
    }

    public void setPrivacyLevel(String privacyLevel) {
        this.privacyLevel = privacyLevel;
    }

    private Boolean active = false;

    public Long getResource() {
        return resource;
    }

    public void setResource(Long resource) {
        this.resource = resource;
    }



    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
