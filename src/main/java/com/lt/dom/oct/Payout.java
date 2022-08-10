package com.lt.dom.oct;


import com.lt.dom.otcenum.EnumPayoutFailureCodes;
import com.lt.dom.otcenum.EnumPayoutMethod;
import com.lt.dom.otcenum.EnumPayoutStatus;

import javax.persistence.*;


@Entity
public class Payout {
    @Version
    private Integer version;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    private EnumPayoutStatus enumPayoutStatus;


    private int amount;
    private boolean automatic;   //Returns true if the payout was created by an automated payout schedule, and false if it was requested manually.

    private String description;

    private long balance_transaction; //ID of the balance transaction that describes the impact of this payout on your account balance.

    private long failure_balance_transaction; //If the payout failed or was canceled, this will be the ID of the balance transaction that reversed the initial balance transaction, and puts the funds from the failed payout back in your balance.


    private EnumPayoutFailureCodes failure_code;//

    //        string
    //Error code explaining reason for payout failure if available. See Types of payout failures for a list of failure codes.

    private String failure_message;//
    //        string
    //Message to user further explaining reason for payout failure if available.

    private EnumPayoutMethod method;
    private String code;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public EnumPayoutStatus getEnumPayoutStatus() {
        return enumPayoutStatus;
    }

    public void setEnumPayoutStatus(EnumPayoutStatus enumPayoutStatus) {
        this.enumPayoutStatus = enumPayoutStatus;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isAutomatic() {
        return automatic;
    }

    public void setAutomatic(boolean automatic) {
        this.automatic = automatic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getBalance_transaction() {
        return balance_transaction;
    }

    public void setBalance_transaction(long balance_transaction) {
        this.balance_transaction = balance_transaction;
    }

    public long getFailure_balance_transaction() {
        return failure_balance_transaction;
    }

    public void setFailure_balance_transaction(long failure_balance_transaction) {
        this.failure_balance_transaction = failure_balance_transaction;
    }

    public EnumPayoutFailureCodes getFailure_code() {
        return failure_code;
    }

    public void setFailure_code(EnumPayoutFailureCodes failure_code) {
        this.failure_code = failure_code;
    }

    public String getFailure_message() {
        return failure_message;
    }

    public void setFailure_message(String failure_message) {
        this.failure_message = failure_message;
    }

    public EnumPayoutMethod getMethod() {
        return method;
    }

    public void setMethod(EnumPayoutMethod method) {
        this.method = method;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
