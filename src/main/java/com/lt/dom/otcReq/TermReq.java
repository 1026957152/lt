package com.lt.dom.otcReq;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.Term;
import com.lt.dom.otcenum.EnumRelatedObjectType;
import com.lt.dom.otcenum.EnumTermType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TermReq {

    private EnumTermType type;

    private String text;
    private Boolean requiresExplicitConsent;

    private Long id;

    public static TermReq from(Term e) {
        TermReq termReq = new TermReq();

        termReq.setId(e.getId());

    //    termResp.setRelatedObjectId(e.getRelatedObjectId());
        termReq.setType(e.getType());
        termReq.setText(e.getText());
        termReq.setRequiresExplicitConsent(e.getRequiresExplicitConsent());

        return termReq;
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



    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
