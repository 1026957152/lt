package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumRequestStatus;
import com.lt.dom.otcenum.EnumRequestType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Request extends Base{


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    private long owner;
    @NotNull
    private Boolean active;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Size(max = 10000)
    private String additional_info;
    @NotNull
    private LocalDateTime applied_at;
    
//##@Column(unique=true) 
private String code;
    private LocalDateTime updated_at;

    public String getAdditional_info() {
        return additional_info;
    }

    public void setAdditional_info(String additional_info) {
        this.additional_info = additional_info;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    @Enumerated(EnumType.STRING)
    private EnumRequestType type;

    private String idId;
    private String url;
    private long source;
    private EnumRequestStatus status;

    public EnumRequestStatus getStatus() {
        return status;
    }

    public void setStatus(EnumRequestStatus status) {
        this.status = status;
    }

    public long getSource() {
        return source;
    }

    public void setSource(long source) {
        this.source = source;
    }

    public EnumRequestType getType() {
        return type;
    }

    public void setType(EnumRequestType type) {
        this.type = type;
    }

    public String getIdId() {
        return idId;
    }

    public void setIdId(String idId) {
        this.idId = idId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getOwner() {
        return owner;
    }

    public void setOwner(long owner) {
        this.owner = owner;
    }

    private EnumRequestType requestType;

    private boolean isDeleted;

    private String justification;

    private String createdAt;

    private String lastModifiedAt;

    private String revision;

    private String name;
    private String description;



    private String policy;


    @Transient
    private List<Long> reviewers;
    @Transient
    private List<Long> team_reviewers;

    public LocalDateTime getApplied_at() {
        return applied_at;
    }

    public void setApplied_at(LocalDateTime applied_at) {
        this.applied_at = applied_at;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setUpdated_at(LocalDateTime updated_at) {

        this.updated_at = updated_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }


    //approvalType



/*    "policy": {
        "id": "request:e77229e8-1f44-4c27-bacb-9a99b7c77af7",
                "status": "active",
                "approvalType": "admin",

      //         "accessType": "everyone",
     //           "access": {
    //    },
        "security": {
            "level": "cloud",
                    "appliesTo": "all"}*/
   // }


/*      "requestType": "SiteRequest",
              "id": "e77229e8-1f44-4c27-bacb-9a99b7c77af7",
              "isDeleted": false,
              "justification": "I require a site for our new product launch.",
              "status": "pending",
              "createdAt": "2018-11-21T12:10:15.123Z",
              "lastModifiedAt": "2018-11-21T12:10:15.123Z",
              "revision": 0,
              "name": "AcmeProductLaunch",

              "description": "Marketing site for Acme New Product Launch.",*/



}
