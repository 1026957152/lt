package com.lt.dom.OctResp;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.Term;
import com.lt.dom.otcenum.EnumRelatedObjectType;
import com.lt.dom.otcenum.EnumTermType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TermResp {

    private EnumTermType type;

    private String text;
    private Boolean requiresExplicitConsent;
    private EnumRelatedObjectType relatedObjectType;

    private Long relatedObjectId;
    private Long id;

    public static TermResp from(Term e) {
        TermResp termResp = new TermResp();

        termResp.setId(e.getId());

    //    termResp.setRelatedObjectId(e.getRelatedObjectId());
        termResp.setType(e.getType());
        termResp.setText(e.getText());
        termResp.setRequiresExplicitConsent(e.getRequiresExplicitConsent());

        return termResp;
    }
    public static TermResp fromToFront(Term e) {
        TermResp termResp = new TermResp();

        termResp.setType(e.getType());
        termResp.setText(e.getText());
        termResp.setRequiresExplicitConsent(e.getRequiresExplicitConsent());

        return termResp;
    }
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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
