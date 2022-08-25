package com.lt.dom.oct;


import com.lt.dom.otcenum.EnumFinancialAccountStatus;
import com.lt.dom.otcenum.EnumIdentityType;
import com.lt.dom.otcenum.EnumUserType;

import javax.persistence.*;

@Entity
public class UserAuthority {
    @Version
    private Integer version;


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    private EnumIdentityType identity_type;
    private String credential;
    private String identifier;
    private long user_id;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public EnumIdentityType getIdentity_type() {
        return identity_type;
    }

    public void setIdentity_type(EnumIdentityType identity_type) {
        this.identity_type = identity_type;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }
}
