package com.lt.dom.oct;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.otcenum.EnumInvitationStatus;
import com.lt.dom.otcenum.EnumInvitationType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Invitation {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @JsonProperty("id")
    private long id;


    private String info;
    private long supplierId;
    private EnumInvitationType type;

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

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }

    public long getSupplierId() {
        return supplierId;
    }

    public void setType(EnumInvitationType type) {
        this.type = type;
    }

    public EnumInvitationType getType() {
        return type;
    }
}
