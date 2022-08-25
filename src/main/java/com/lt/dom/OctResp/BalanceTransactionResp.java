package com.lt.dom.OctResp;


import com.lt.dom.oct.BalanceTransaction;
import com.lt.dom.otcenum.EnumBalanceTranStatus;
import com.lt.dom.otcenum.EnumTranType;

import javax.persistence.*;
import java.time.LocalDateTime;


public class BalanceTransactionResp {  //余额结算

    private EnumTranType flowType;
    private long balance;
    private int fee;
    private int net;
    private String sourceId;
    private long source;
    private String code;
    private String flowType_text;
    private String status_text;

    public static BalanceTransactionResp from(BalanceTransaction e) {
        BalanceTransactionResp balanceTransaction = new BalanceTransactionResp();
        balanceTransaction.setBalance(e.getBalance());
        balanceTransaction.setCode(e.getCode());
        balanceTransaction.setAmount(e.getAmount());
        balanceTransaction.setFlowType_text(e.getFlowType().toString());
        balanceTransaction.setFlowType(e.getFlowType());
        balanceTransaction.setStatus(e.getStatus());
        balanceTransaction.setStatus_text(e.getStatus().toString());
        balanceTransaction.setFee(e.getFee());
        return balanceTransaction;
    }

    public EnumTranType getFlowType() {
        return flowType;
    }

    public void setFlowType(EnumTranType flowType) {
        this.flowType = flowType;
    }
/*    object
            string
    值为 "balance_transfer"。
    app
            string
    对应 app 对象的 id，查看 如何获取App ID。*/

    private boolean livemode; //是否是 live 模式。
    private EnumBalanceTranStatus status;//目前值为转账成功：succeeded。
    private int amount;//接收方收到转账的金额，单位为分。


    private int user_fee;//向发起转账的用户额外收取的手续费，单位为分，且值需小于 amount。
    private String user;//转出方的 user 对象的 id。

/*
    private String user_balance_transaction;//转账关联的转出方 balance_transaction 对象的 id。
    private String recipient_balance_transaction;//转账关联的接收方 balance_transaction 对象的 id。
    private String description;//附加说明，最多 60 个 Unicode 字符。
*/

    private int time_credited;//入账完成时间。
    private int time_succeeded;//结算完成时间。


    private boolean refunded;//余额结算金额是否有退款。

    private int amount_refunded;//已退款的余额结算金额，单位分。

    private long charge;//结算关联的 charge 对象的 id
    private long order_no;//结算关联的 charge 对象内的 order_no。
    private long transaction_no;//结算关联的 charge 对象内的 transaction_no
    private String failure_msg;//结算失败信息
    private LocalDateTime posted_at;

    public LocalDateTime getPosted_at() {
        return posted_at;
    }

    public void setPosted_at(LocalDateTime posted_at) {
        this.posted_at = posted_at;
    }



    public long getTransaction_no() {
        return transaction_no;
    }

    public void setTransaction_no(long transaction_no) {
        this.transaction_no = transaction_no;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public long getBalance() {
        return balance;
    }

    public void setStatus(EnumBalanceTranStatus status) {

        this.status = status;
    }

    public EnumBalanceTranStatus getStatus() {
        return status;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public int getFee() {
        return fee;
    }

    public void setNet(int net) {
        this.net = net;
    }

    public int getNet() {
        return net;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSource(long source) {
        this.source = source;
    }

    public long getSource() {
        return source;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setFlowType_text(String flowType_text) {
        this.flowType_text = flowType_text;
    }

    public String getFlowType_text() {
        return flowType_text;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public String getStatus_text() {
        return status_text;
    }
}
