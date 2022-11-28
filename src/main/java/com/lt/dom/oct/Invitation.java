package com.lt.dom.oct;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.otcenum.EnumInvitateRequestType;
import com.lt.dom.otcenum.EnumInvitationStatus;
import com.lt.dom.otcenum.EnumInvitationType;

import javax.persistence.*;

@Entity
public class Invitation extends Base{


    private String code;

    @Enumerated(EnumType.STRING)
    private EnumInvitateRequestType invitateRequestType;
    private String groupId;
    private long partner;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String info;
    private long supplier;

    @Enumerated(EnumType.STRING)
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

    private String email;//	string	yes (if user_id isn’t provided)	The email of the new member or multiple emails separated by commas.
    private String user_id;//	integer/string	yes (if email isn’t provided)	The ID of the new member or multiple IDs separated by commas. Introduced in GitLab 14.10.
    private String access_level;///	integer	yes	A valid access level
    private String expires_at;//	string	no	A date string in the format YEAR-MONTH-DAY
    private String invite_source;//	string	no	The source of the invitation that starts the member creation process. See this issue.
    private String  tasks_to_be_done;//	array of strings	no	Tasks the inviter wants the member to focus on. The tasks are added as issues to a specified project. The possible values are: ci, code and issues. If specified, requires tasks_project_id. Introduced in GitLab 14.6
    private String  tasks_project_id;//	integer


    @Enumerated(EnumType.STRING)
    private EnumInvitationStatus status;//	integer

    public EnumInvitationStatus getStatus() {
        return status;
    }

    public void setStatus(EnumInvitationStatus status) {
        this.status = status;
    }

    private Invitation(String firstName, String lastName, Integer seatNumber) {
        this.email = lastName;
        this.access_level = firstName;

    }

    public Invitation() {

    }

    public static Invitation from(String firstName, String lastName, Integer seatNumber) {
        return new Invitation(firstName, lastName, seatNumber);
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
}
