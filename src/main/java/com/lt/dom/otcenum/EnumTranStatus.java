package com.lt.dom.otcenum;

public enum EnumTranStatus {//交易类型

    open(""),
    //The initial state for all Transactions. The Transaction results in updates to the sub-balance amounts, but the current balance is not affected until the Transaction posts.

    posted(""),
    //Funds have successfully entered or left the account. The current balance was affected.

    void_(""),
    //The Transaction never impacted the balance. For example, a Transaction would enter this state if an OutboundPayment was initiated but then canceled before the funds left the account.

    ;


    EnumTranStatus(String 入账退款) {

    }
}
