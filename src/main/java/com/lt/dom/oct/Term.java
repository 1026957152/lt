package com.lt.dom.oct;


import com.lt.dom.otcenum.EnumRelatedObjectType;
import com.lt.dom.otcenum.EnumTermType;

import javax.persistence.Column;
import javax.persistence.Entity;


//https://metmuseum.github.io/
@Entity
public class Term extends Base {


    private EnumTermType type;

    private String text;
    private Boolean requiresExplicitConsent;
    private EnumRelatedObjectType relatedObjectType;

    private Long relatedObjectId;

    public EnumTermType getType() {
        return type;
    }

    public void setType(EnumTermType type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getRequiresExplicitConsent() {
        return requiresExplicitConsent;
    }

    public void setRequiresExplicitConsent(Boolean requiresExplicitConsent) {
        this.requiresExplicitConsent = requiresExplicitConsent;
    }

    public EnumRelatedObjectType getRelatedObjectType() {
        return relatedObjectType;
    }

    public void setRelatedObjectType(EnumRelatedObjectType relatedObjectType) {
        this.relatedObjectType = relatedObjectType;
    }

    public Long getRelatedObjectId() {
        return relatedObjectId;
    }

    public void setRelatedObjectId(Long relatedObjectId) {
        this.relatedObjectId = relatedObjectId;
    }
}
