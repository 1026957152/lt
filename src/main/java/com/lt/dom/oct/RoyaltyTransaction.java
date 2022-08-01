package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumRoyaltyTransactionStatus;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class RoyaltyTransaction {

    private EnumRoyaltyTransactionStatus status;

    @Id
    private String id;
    private String object = "royalty_transaction";
    private String amount;
    private String settle_account;
    private String source_user;
    private String recipient_app;
    private String failure_msg;

    @Transient
    private Transfer transfer;
    private String royalty_settlement;



    public EnumRoyaltyTransactionStatus getStatus() {
        return status;
    }

    public void setStatus(EnumRoyaltyTransactionStatus status) {
        this.status = status;
    }

    public RoyaltyTransaction() {
    }

    public RoyaltyTransaction(long id, long recipient, int amount) {


    }
}
