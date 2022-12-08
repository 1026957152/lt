package com.lt.dom.vo;

import com.lt.dom.otcenum.EnumRelatedObjectType;

public class RedemptionForCustomerVo {

    private Long id;
    private String realName;
    private String code;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRealName() {
        return realName;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
