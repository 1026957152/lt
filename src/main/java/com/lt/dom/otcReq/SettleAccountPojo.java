package com.lt.dom.otcReq;

public class SettleAccountPojo {


    private String channel;//	结算账号渠道名称，目前支持：alipay（支付宝）、wx（微信 App）、wx_pub（微信 JSAPI）、wx_lite（微信小程序）、bank_account（银行卡）。
    private String recipient;//	脱敏的结算账号接收者信息，详情参见请求参数 recipient 部分。object
    private String open_bank_code;//	脱敏的结算账号接收者信息，详情参见请求参数 recipient 部分。object
    private String account;//	脱敏的结算账号接收者信息，详情参见请求参数 recipient 部分。object
    private String name;//	脱敏的结算账号接收者信息，详情参见请求参数 recipient 部分。object
    private String accountName;
    private String bankAccountNumber;
    private String bankName;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getOpen_bank_code() {
        return open_bank_code;
    }

    public void setOpen_bank_code(String open_bank_code) {
        this.open_bank_code = open_bank_code;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
