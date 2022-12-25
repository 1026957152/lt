package com.lt.dom.oct;


import com.lt.dom.otcenum.EnumIdentityType;

import javax.persistence.*;

@Entity
public class UserAuthority extends Base{

    @Enumerated(EnumType.STRING)
    private EnumIdentityType identityType;
    private String credential;
    private String identifier;
    private Long userId;





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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
