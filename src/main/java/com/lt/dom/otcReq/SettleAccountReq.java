package com.lt.dom.otcReq;

import com.lt.dom.domain.SettleAccount;
import com.lt.dom.oct.Base;


public class SettleAccountReq extends Base {


    private String channel;//	结算账号渠道名称，目前支持：alipay（支付宝）、wx（微信 App）、wx_pub（微信 JSAPI）、wx_lite（微信小程序）、bank_account（银行卡）。
    private String recipient;//	脱敏的结算账号接收者信息，详情参见请求参数 recipient 部分。object

    private String accountName;
    private String bankAccountNumber;
    private String bankName;

    public static SettleAccountReq from(SettleAccount settleAccount) {

        if(settleAccount != null){
            SettleAccountReq settleAccountReq = new SettleAccountReq();
            settleAccountReq.setAccountName(settleAccount.getAccountName());
            settleAccountReq.setBankAccountNumber(settleAccount.getBankAccount());
            settleAccountReq.setBankName(settleAccount.getBankName());
            return settleAccountReq;
        }else{
            SettleAccountReq settleAccountReq = new SettleAccountReq();
            return settleAccountReq;
        }

    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
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
