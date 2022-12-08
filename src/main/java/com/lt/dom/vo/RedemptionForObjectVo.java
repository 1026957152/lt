package com.lt.dom.vo;

import com.lt.dom.otcenum.EnumCardType;
import com.lt.dom.otcenum.EnumRelatedObjectType;

public class RedemptionForObjectVo {


    private Long relatedObjectId;
    private EnumRelatedObjectType relatedObjectType;
    private String relatedObjectCode;
    private String relatedObject_subType;
    private String lable;

    public Long getRelatedObjectId() {
        return relatedObjectId;
    }

    public void setRelatedObjectId(Long relatedObjectId) {
        this.relatedObjectId = relatedObjectId;
    }

    public EnumRelatedObjectType getRelatedObjectType() {
        return relatedObjectType;
    }

    public void setRelatedObjectType(EnumRelatedObjectType relatedObjectType) {
        this.relatedObjectType = relatedObjectType;
    }

    public String getRelatedObjectCode() {
        return relatedObjectCode;
    }

    public void setRelatedObjectCode(String relatedObjectCode) {
        this.relatedObjectCode = relatedObjectCode;
    }

    public String getRelatedObject_subType() {
        return relatedObject_subType;
    }

    public void setRelatedObject_subType(String relatedObject_subType) {
        this.relatedObject_subType = relatedObject_subType;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }
}
