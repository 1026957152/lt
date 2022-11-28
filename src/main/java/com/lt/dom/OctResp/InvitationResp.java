package com.lt.dom.OctResp;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.Base;
import com.lt.dom.oct.Invitation;
import com.lt.dom.oct.Supplier;
import com.lt.dom.otcenum.EnumInvitateRequestType;
import com.lt.dom.otcenum.EnumInvitationStatus;
import com.lt.dom.otcenum.EnumInvitationType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvitationResp extends BaseResp {

    private String code;
    private EnumInvitateRequestType invitateRequestType;
    private String groupId;
    private long partner;
    private String type_text;
    private String invitateRequestType_text;
    private String partnerCode;

    public static InvitationResp from(Invitation e, Supplier supplier1) {
        InvitationResp invitationResp = new InvitationResp();
        invitationResp.setCode(e.getCode());
        invitationResp.setInvitateRequestType_text(e.getInvitateRequestType().toString());
        invitationResp.setStatus(e.getStatus());
        invitationResp.setPartnerName(supplier1.getName());
        invitationResp.setPartnerCode(supplier1.getCode());
        invitationResp.setType_text(e.getType().toString());
        invitationResp.setCreatedDate(e.getCreatedDate());
        invitationResp.setModifiedDate(e.getModifiedDate());
        return invitationResp;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String info;
    private long supplier;
    private EnumInvitationType type;

    private String partnerName;

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partner_name) {
        this.partnerName = partner_name;
    }

    private EnumInvitationType invitationType;
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }




    private EnumInvitationStatus status;//	integer

    public EnumInvitationStatus getStatus() {
        return status;
    }

    public void setStatus(EnumInvitationStatus status) {
        this.status = status;
    }




    public void setSupplier(long supplierId) {
        this.supplier = supplierId;
    }

    public long getSupplier() {
        return supplier;
    }

    public void setType(EnumInvitationType type) {
        this.type = type;
    }

    public EnumInvitationType getType() {
        return type;
    }

    public void setInvitateRequestType(EnumInvitateRequestType invitateRequestType) {
        this.invitateRequestType = invitateRequestType;
    }

    public EnumInvitateRequestType getInvitateRequestType() {
        return invitateRequestType;
    }



    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setPartner(long partner) {
        this.partner = partner;
    }

    public long getPartner() {
        return partner;
    }

    public void setType_text(String type_text) {
        this.type_text = type_text;
    }

    public String getType_text() {
        return type_text;
    }

    public void setInvitateRequestType_text(String invitateRequestType_text) {
        this.invitateRequestType_text = invitateRequestType_text;
    }

    public String getInvitateRequestType_text() {
        return invitateRequestType_text;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getPartnerCode() {
        return partnerCode;
    }
}
