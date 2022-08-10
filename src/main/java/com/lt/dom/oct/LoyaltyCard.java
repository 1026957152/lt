package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumLoyaltyCardAccountType;
import com.lt.dom.otcenum.EnumLoyaltyCardStatus;
import com.lt.dom.otcenum.EnumLoyaltyCardStatusInfo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class LoyaltyCard {
    @Version
    private Integer version;
    @Id
    private long id;
    private EnumLoyaltyCardAccountType type;   //accountType
    // The current loyalty card account type. WebOnly Legacy Insider Premiere Associate


    private EnumLoyaltyCardStatus status;
    private EnumLoyaltyCardStatusInfo statusInfo;
    private Exception expirationDate;


    private int balance;//	The current loyalty card balance as a decimal in USD.
    private int pointsEarned;//	The current loyalty card balance as rewards points.

    private long user;




    private long campaign_id;//




}
