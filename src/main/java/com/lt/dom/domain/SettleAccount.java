package com.lt.dom.domain;

import com.lt.dom.oct.Base;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class SettleAccount extends Base {



    private String object;//	值为 "settle_account"。
    private String  created;//	创建时的 Unix 时间戳。timestamp
    private String channel;//	结算账号渠道名称，目前支持：alipay（支付宝）、wx（微信 App）、wx_pub（微信 JSAPI）、wx_lite（微信小程序）、bank_account（银行卡）。
    private String recipient;//	脱敏的结算账号接收者信息，详情参见请求参数 recipient 部分。object
    private long supplierId;
    private LocalDateTime created_at;
    private String accountName;
    private String bankAccount;
    private String bankName;

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }

    public long getSupplierId() {
        return supplierId;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankName() {
        return bankName;
    }


/*    {
        "id": "320217022818035400000601",
            "object": "settle_account",
            "created": 1488276234,
            "livemode": true,
            "channel": "bank_account",
            "recipient": {
        "open_bank_code": "0102",
                "account": "622262***2145",
                "name": "***哥"
    }
    }*/
}
