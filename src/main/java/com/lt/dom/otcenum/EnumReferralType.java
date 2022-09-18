package com.lt.dom.otcenum;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

public enum EnumReferralType {

    fill_up_passager_info("/page/user?id=%s","/page/user?url=%s"),
    barcode("barcode","");



    private String path;
    private String url;
    EnumReferralType(String path,String url) {

        this.path = path;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
