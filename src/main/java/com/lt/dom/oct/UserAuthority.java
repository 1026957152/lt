package com.lt.dom.oct;


import com.lt.dom.otcenum.EnumIdentityType;

import javax.persistence.*;

@Entity
public class UserAuthority extends Base{

    @Enumerated(EnumType.STRING)
    private EnumIdentityType identityType;
    private String credential;
    private String identifier;
    private long user_id;





    public EnumIdentityType getIdentityType() {
        return identityType;
    }

    public void setIdentityType(EnumIdentityType identity_type) {
        this.identityType = identity_type;
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
